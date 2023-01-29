package com.example.tddapp.markdown

import com.example.tddapp.markdown.Markdown.ParseResult.Style
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.core.text.italic
import androidx.core.text.underline
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MarkdownTest {

    private val boldMarker = "**"
    private val italicMarker = "$$"
    private val underlineMarker = "%%"

    private lateinit var parser: Markdown.Parser


    @Before
    fun setUp() {
        parser = Markdown.Parser.Base(
            boldMarker = boldMarker,
            italicMarker = italicMarker,
            underlineMarker = underlineMarker
        )
    }

    @Test
    fun `simple`() {

        val testString =
            "Hello! My name is ${boldMarker}Alexander${boldMarker},\nand I`m ${italicMarker}android developer${italicMarker},\nThis is my git hub: \n${underlineMarker}https://github.com/AlezZgo${underlineMarker}"

        val actual = parser.parse(testString)

        val expected: List<Markdown.ParseResult> = listOf(
            Markdown.ParseResult.Base(
                text = "Hello! My name is ",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "Alexander",
                styles = listOf(Style.BOLD)
            ),
            Markdown.ParseResult.Base(
                text = ",\n and I`m ",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "android developer",
                styles = listOf(Style.ITALIC)
            ),
            Markdown.ParseResult.Base(
                text = ",\n This is my git hub: \n",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "https://github.com/AlezZgo",
                styles = listOf(Style.UNDERLINE)
            ),
        )

        assertEquals(expected, actual)

    }

    @Test
    fun `complex with prefix and suffix`() {

        val testString =
            "Hello! My name is ${boldMarker}${boldMarker}Alexander${boldMarker},\nand I`m ${italicMarker}android developer${italicMarker}${italicMarker},\nThis is my git hub: \n${underlineMarker}${boldMarker}${italicMarker}https://github.com/AlezZgo${italicMarker}${boldMarker}${underlineMarker}"

        val actual = parser.parse(testString)

        val expected: List<Markdown.ParseResult.Base> = listOf(
            Markdown.ParseResult.Base(
                text = "Hello! My name is ",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "${boldMarker}Alexander",
                styles = listOf(Style.BOLD)
            ),
            Markdown.ParseResult.Base(
                text = ",\n and I`m ",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "android developer${italicMarker}",
                styles = listOf(Style.ITALIC)
            ),
            Markdown.ParseResult.Base(
                text = ",\n This is my git hub: \n",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "https://github.com/AlezZgo",
                styles = listOf(Style.BOLD,Style.ITALIC,Style.UNDERLINE)
            ),
        )

        assertEquals(expected, actual)

    }

    @Test
    fun `phrases duplicates`() {
        val testString = "This is test some text for ${boldMarker}test${boldMarker}"

        val actual = parser.parse(testString)

        val expected: List<Markdown.ParseResult.Base> = listOf(
            Markdown.ParseResult.Base(
                text = "This is test some text for ",
                styles = emptyList()
            ),
            Markdown.ParseResult.Base(
                text = "test",
                styles = listOf(Style.BOLD)
            ),
        )

        assertEquals(expected, actual)

    }

    @Test
    fun `intersecting markdown text`() {

        val testString =
            "${boldMarker}This is ${italicMarker}Some text for${boldMarker} test${italicMarker}"

        val actual = parser.parse(testString)

        val expected: List<Markdown.ParseResult.Base> = listOf(
            Markdown.ParseResult.Base(
                text = "This is ",
                styles = listOf(Style.BOLD)
            ),
            Markdown.ParseResult.Base(
                text = "Some text for",
                styles = listOf(Style.BOLD,Style.ITALIC)
            ),
            Markdown.ParseResult.Base(
                text = " test",
                styles = listOf(Style.ITALIC)
            ),
        )

        assertEquals(expected, actual)

    }

}
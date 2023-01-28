package com.example.tddapp.markdown

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.core.text.italic
import androidx.core.text.underline
import org.junit.Assert.*
import org.junit.Test


class MarkdownTest {

    private val boldMarker = "**"
    private val italicMarker = "$$"
    private val underlineMarker = "%%"

    @Test
    fun `simple markdown text`() {

        val testString =
            "Hello! My name is ${boldMarker}Alexander${boldMarker},\nand I`m ${italicMarker}android developer${italicMarker},\nThis is my git hub: \n${underlineMarker}https://github.com/AlezZgo${underlineMarker}"

        val parser = Markdown.Parser.Base(
            boldMarker = boldMarker,
            italicMarker = italicMarker,
            underlineMarker = underlineMarker
        )

        val actual = parser.parse(testString)

        val expected: SpannableStringBuilder = SpannableStringBuilder().append(
            "Hello! My name is ",
        ).bold {
            append("Alexander")
        }.append(
            ",\n and I`m "
        ).italic {
            append("android developer")
        }.append(
            ",\n This is my git hub: \n"
        ).underline {
            append("https://github.com/AlezZgo")
        }

        assertEquals(expected, actual)

    }

    @Test
    fun `complex markdown text`() {

        val testString =
            "Hello! My name is ${boldMarker}${boldMarker}Alexander${boldMarker},\nand I`m ${italicMarker}android developer${italicMarker}${italicMarker},\nThis is my git hub: \n${underlineMarker}${boldMarker}${italicMarker}https://github.com/AlezZgo${italicMarker}${boldMarker}${underlineMarker}"

        val parser = Markdown.Parser.Base(
            boldMarker = boldMarker,
            italicMarker = italicMarker,
            underlineMarker = underlineMarker
        )

        val actual = parser.parse(testString)

        val expected: SpannableStringBuilder = SpannableStringBuilder().append(
            "Hello! My name is $boldMarker",
        ).bold {
            append("Alexander")
        }.append(
            ",\n and I`m "
        ).italic {
            append("android developer")
        }.append(
            "$italicMarker,\n This is my git hub: \n"
        ).underline {
            bold {
                italic {
                    append("https://github.com/AlezZgo")
                }
            }

        }

        assertEquals(expected, actual)

    }

}
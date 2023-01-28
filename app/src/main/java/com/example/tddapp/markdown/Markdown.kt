package com.example.tddapp.markdown

import android.graphics.Typeface.BOLD
import android.graphics.Typeface.ITALIC
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.core.text.inSpans

interface Markdown {

    interface Parser {

        fun parse(text: String): SpannableStringBuilder

        class Base(
            private val boldMarker: String? = null,
            private val italicMarker: String? = null,
            private val underlineMarker: String? = null
        ) : Parser {

            override fun parse(text: String): SpannableStringBuilder {
                val result = SpannableStringBuilder()

                var currentIndex = 0

                var boldIsActive = false
                var italicIsActive = false
                var underlineIsActive = false

                val simpleTextToAdd = StringBuilder("")
                val textToAppendWithFormat = SpannableStringBuilder("")

                while (currentIndex!=text.length-1) {
                    val char = text[currentIndex]
                    when (text.substring(currentIndex, currentIndex + 1)) {
                        boldMarker -> {
                            if (boldIsActive) {
                                setSpannableWithFormat(
                                    result,
                                    textToAppendWithFormat,
                                    boldIsActive,
                                    italicIsActive,
                                    underlineIsActive
                                )
                                boldIsActive = false
                                currentIndex += 2
                            } else {
                                boldIsActive = true
                                currentIndex += 2
                            }

                        }
                        italicMarker -> {
                            if (italicIsActive) {
                                setSpannableWithFormat(
                                    result,
                                    textToAppendWithFormat,
                                    boldIsActive,
                                    italicIsActive,
                                    underlineIsActive
                                )
                                italicIsActive = false
                                currentIndex += 2
                            } else {
                                italicIsActive = true
                                currentIndex += 2
                            }
                        }
                        underlineMarker -> {
                            if (underlineIsActive) {
                                setSpannableWithFormat(
                                    result,
                                    textToAppendWithFormat,
                                    boldIsActive,
                                    italicIsActive,
                                    underlineIsActive
                                )
                                underlineIsActive = false
                                currentIndex += 2
                            } else {
                                underlineIsActive = true
                                currentIndex += 2
                            }
                        }
                        else -> {
                            if (noActiveFormatters(
                                    boldIsActive,
                                    italicIsActive,
                                    underlineIsActive
                                )
                            ) {
                                simpleTextToAdd.append(char)
                            } else {
                                textToAppendWithFormat.append(char)
                            }
                            currentIndex++
                        }
                    }


                }


                return result
            }

            private fun noActiveFormatters(
                boldIsActive: Boolean,
                italicIsActive: Boolean,
                underlineIsActive: Boolean
            ) = boldIsActive || italicIsActive || underlineIsActive

            companion object{
                fun setSpannableWithFormat(
                    spannable: SpannableStringBuilder,
                    text: SpannableStringBuilder,
                    boldIsActive: Boolean,
                    italicIsActive: Boolean,
                    underlineIsActive: Boolean
                ) : SpannableStringBuilder {

                    if (boldIsActive) {
                        text.setSpan(
                            StyleSpan(BOLD),
                            0,
                            text.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    if (italicIsActive) {
                        text.setSpan(
                            StyleSpan(ITALIC),
                            0,
                            text.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    if (underlineIsActive) {
                        text.setSpan(
                            UnderlineSpan(),
                            0,
                            text.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    return spannable.append(text)

                }

            }


        }

    }
}

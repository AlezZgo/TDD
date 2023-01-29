package com.example.tddapp.markdown

interface Markdown {

    interface Parser {

        fun parse(text: String): List<ParseResult>

        class Base(
            private val boldMarker: String? = null,
            private val italicMarker: String? = null,
            private val underlineMarker: String? = null
        ) : Parser {

            override fun parse(text: String): List<ParseResult> {
                val result = mutableListOf <ParseResult>()

                var currentIndex = 0

                var boldIsActive = false
                var italicIsActive = false
                var underlineIsActive = false

                val simpleTextToAdd = StringBuilder()
                val textToAppendWithFormat = StringBuilder()

                while (currentIndex <= text.length - 1) {
                    val char = text[currentIndex]
                    when (text.substring(currentIndex, currentIndex + 1)) {
                        boldMarker -> {
                            if (boldIsActive) {
                                //todo
                                boldIsActive = false
                                currentIndex += 2
                            } else {
                                boldIsActive = true
                                currentIndex += 2
                            }

                        }
                        italicMarker -> {
                            if (italicIsActive) {
                                //todo
                                italicIsActive = false
                                currentIndex += 2
                            } else {
                                italicIsActive = true
                                currentIndex += 2
                            }
                        }
                        underlineMarker -> {
                            if (underlineIsActive) {
                                //todo
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

        }

    }

    interface ParseResult {

        data class Base(
            private val text: String,
            private val styles: List<Style>
        ) : ParseResult

        enum class Style {
            BOLD, ITALIC, UNDERLINE
        }

    }


}

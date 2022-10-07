package com.infopolis.infopolis.util

import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun String.trimName() = this.substringBefore(",").replace(" ", "-").lowercase()

fun String.removeHtml() = this.replace("<[^>]*>".toRegex(), "").removeNewLine().removeDuplicateWhiteSpace()

fun String.removeNewLine() = this.replace("\n", "")

fun String.removeDuplicateWhiteSpace() = this.replace("\\s+".toRegex(), " ")

fun String.formatText() = this.removeHtml().removeNewLine().removeDuplicateWhiteSpace().trim()

fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

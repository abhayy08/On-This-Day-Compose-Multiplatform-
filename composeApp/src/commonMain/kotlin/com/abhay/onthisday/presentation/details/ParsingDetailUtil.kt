package com.abhay.onthisday.presentation.details

sealed class DetailItem {
    data class Heading(val text: String, val level: Int) : DetailItem()
    data class Paragraph(val text: String) : DetailItem()
}

fun parseDetailText(detail: String): List<DetailItem> {
    return detail.lines().mapNotNull { line ->
        val trimmed = line.trim()
        if (trimmed.isBlank()) return@mapNotNull null
        if (trimmed.startsWith("=")) {
            val level = trimmed.takeWhile { it == '=' }.length
            val text = trimmed.trim('=').trim()
            DetailItem.Heading(text, level)
        } else {
            DetailItem.Paragraph(trimmed)
        }
    }
}


package com.abhay.onthisday.data.mapper

import com.abhay.onthisday.data.dto.Page
import com.abhay.onthisday.data.dto.PageSummary
import com.abhay.onthisday.domain.model.DetailedEvent
import com.abhay.onthisday.domain.model.Event

fun Page.toDomainEvent(): Event {
    return Event(
        identifierTitle = title,
        title = normalizedtitle,
        description = description ?: "",
        timeStamp = timestamp ?: "",
        extract = extract ?: "",
        thumbnail = thumbnail?.source ?: "",
        originalImage = originalimage?.source ?: "",
        contentUrl = ""
    )
}

fun PageSummary.toDetailedEvent(): DetailedEvent {
    return DetailedEvent(
        title = title,
        detail = extract ?: ""
    )
}
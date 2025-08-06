package com.abhay.onthisday.data.repository

import com.abhay.onthisday.data.mapper.toDetailedEvent
import com.abhay.onthisday.data.network.RemoteDataSource
import com.abhay.onthisday.domain.model.Event
import com.abhay.onthisday.domain.repository.RemoteRepository
import com.abhay.onthisday.domain.util.DataError
import com.abhay.onthisday.domain.util.Result
import com.abhay.onthisday.domain.util.map
import com.abhay.onthisday.data.mapper.toDomainEvent
import com.abhay.onthisday.domain.model.DetailedEvent

class RemoteDataRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): RemoteRepository {
    override suspend fun getEventsOnThisDay(day: Int, month: Int): Result<List<Event>, DataError> {
        return remoteDataSource.getEventsForThisDay(day, month).map { dto ->
            dto.events.map { event ->
                event.pages[0].toDomainEvent()
            }.shuffled()
        }
    }

    override suspend fun getDetailsOnTopic(topic: String): Result<DetailedEvent, DataError> {
        return remoteDataSource.getDetailsOnTopic(topic).map { dto ->
            dto.query.pages.values.firstOrNull()?.toDetailedEvent() ?: DetailedEvent(title = "", detail = "")
        }
    }


}
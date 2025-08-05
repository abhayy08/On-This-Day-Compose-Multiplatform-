package com.abhay.onthisday.domain.repository

import com.abhay.onthisday.domain.model.Event
import com.abhay.onthisday.domain.util.DataError
import com.abhay.onthisday.domain.util.Result

interface RemoteRepository {

    suspend fun getEventsOnThisDay(day: Int, month: Int): Result<List<Event>, DataError>

}

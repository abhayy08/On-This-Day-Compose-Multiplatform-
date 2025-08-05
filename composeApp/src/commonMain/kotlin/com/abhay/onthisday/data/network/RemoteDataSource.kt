package com.abhay.onthisday.data.network

import com.abhay.onthisday.data.dto.ApiResponseDto
import com.abhay.onthisday.domain.util.DataError
import com.abhay.onthisday.domain.util.Result

interface RemoteDataSource {
    suspend fun getEventsForThisDay(
        day: Int,
        month: Int
    ): Result<ApiResponseDto, DataError>
}
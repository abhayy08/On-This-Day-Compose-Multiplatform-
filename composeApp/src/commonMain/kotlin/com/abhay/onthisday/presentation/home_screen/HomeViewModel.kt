package com.abhay.onthisday.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.onthisday.domain.model.Event
import com.abhay.onthisday.domain.repository.RemoteRepository
import com.abhay.onthisday.domain.util.onError
import com.abhay.onthisday.domain.util.onSuccess
import com.abhay.onthisday.presentation.toUiText
import com.abhay.onthisday.presentation.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    private var cachedEvents = emptyList<Event>()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
        .onStart {
            if(cachedEvents.isEmpty()) {
                getEventsForThisDay()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )


    fun getEventsForThisDay(){
        val timezone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timezone).date
        val day = today.dayOfMonth
        val month = today.monthNumber

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            remoteRepository.getEventsOnThisDay(day, month)
                .onSuccess { events ->
                    cachedEvents = events
                    _uiState.update {
                            it.copy(
                            events = events,
                            isLoading = false,
                            error = null
                        )
                    }
                    println(events)
                }
                .onError { error ->
                    _uiState.update {
                        it.copy(
                            events = cachedEvents,
                            isLoading = false,
                            error = error.toUiText()
                        )
                    }
                }

        }
    }

}

data class HomeUiState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)

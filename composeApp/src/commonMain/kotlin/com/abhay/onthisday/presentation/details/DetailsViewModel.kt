package com.abhay.onthisday.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.onthisday.domain.repository.RemoteRepository
import com.abhay.onthisday.domain.util.onError
import com.abhay.onthisday.domain.util.onSuccess
import com.abhay.onthisday.presentation.toUiText
import com.abhay.onthisday.presentation.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun getDetailOf(identifierTitle: String? = null) {
        _uiState.update { it.copy(isLoading = true)}
        viewModelScope.launch(Dispatchers.IO) {
            if(identifierTitle != null) {
                remoteRepository.getDetailsOnTopic(identifierTitle)
                    .onSuccess { data ->
                        _uiState.update {
                            it.copy(
                                error = null,
                                title = data.title,
                                detail = data.detail,
                                isLoading = false
                            )
                        }
                    }
                    .onError { error ->
                        _uiState.update {
                            it.copy(
                                error = error.toUiText(),
                                isLoading = false
                            )
                        }
                    }
            }
        }
    }
}

data class DetailsUiState(
    val title: String = "",
    val detail: String = "",
    val isLoading: Boolean = false,
    val error: UiText? = null
)

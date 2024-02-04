package com.example.movieapplicationv2.ui.listscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationv2.common.UiState
import com.example.movieapplicationv2.data.MovieRepository
import com.example.movieapplicationv2.model.musiclist.MusicEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
class MovieListScreenViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MusicEntity>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MusicEntity>> get() = _uiState

    fun getMusicTrack(searchArtis: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _uiState.value = UiState.Loading
                _uiState.value = UiState.Success(repository.getMusicTrack(searchArtis))
                _uiState.value =
                    if (repository.getMusicTrack(searchArtis).data?.isEmpty() == true) UiState.Error("error") else UiState.Success(
                        repository.getMusicTrack(searchArtis)
                    )
            }
        }
    }

    private val _buttonState = MutableStateFlow<Boolean>(true)
    val buttonStateFlow: StateFlow<Boolean> get() = _buttonState

    fun setButtonState(isChange: Boolean) {
        _buttonState.value = isChange
    }

    fun resetButton() {
        _buttonState.value = false
    }

    private val _musicStateId = MutableStateFlow<Double>(0.0)
    val musicStatePosition: StateFlow<Double> get() = _musicStateId

    fun setMusicPosition(id: Double) {
        _musicStateId.value = id
    }

    fun getMusicId(): StateFlow<Double> {
        return musicStatePosition
    }
}
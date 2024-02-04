package com.example.movieapplicationv2.ui.detailscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationv2.common.UiState
import com.example.movieapplicationv2.data.MovieRepository
import com.example.movieapplicationv2.model.movie.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alo-BambangHariantoSianturi on 27/11/23.
 */
class DetailScreenViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MovieDetail>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MovieDetail>> get() = _uiState

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _uiState.value = UiState.Loading
                _uiState.value = UiState.Success(repository.getDetailMovie(movieId))
                _uiState.value =
                    if (repository.getDetailMovie(movieId).id.equals(null)) UiState.Error("error") else UiState.Success(
                        repository.getDetailMovie(movieId)
                    )
            }
        }
    }
}
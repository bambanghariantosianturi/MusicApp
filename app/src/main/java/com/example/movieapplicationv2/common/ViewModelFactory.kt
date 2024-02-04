package com.example.movieapplicationv2.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapplicationv2.data.MovieRepository
import com.example.movieapplicationv2.ui.detailscreen.viewmodel.DetailScreenViewModel
import com.example.movieapplicationv2.ui.listscreen.viewmodel.MovieListScreenViewModel

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListScreenViewModel::class.java)) {
            return MovieListScreenViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
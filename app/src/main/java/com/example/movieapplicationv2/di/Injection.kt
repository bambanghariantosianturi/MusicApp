package com.example.movieapplicationv2.di

import com.example.movieapplicationv2.data.MovieRepository

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
object Injection {
    fun provideRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}
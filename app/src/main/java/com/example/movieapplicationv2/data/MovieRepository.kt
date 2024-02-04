package com.example.movieapplicationv2.data

import com.example.movieapplicationv2.data.network.ApiConfig
import com.example.movieapplicationv2.model.movie.MovieDetail
import com.example.movieapplicationv2.model.musiclist.MusicEntity

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
class MovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository = instance ?: synchronized(this) {
            MovieRepository().apply {
                instance = this
            }
        }
    }

    suspend fun getMusicTrack(search: String): MusicEntity {
        val response = ApiConfig.provideApiService().getMusicTrack(search)
        val data = response.body()
        return data!!
    }

    suspend fun getDetailMovie(movieId: String): MovieDetail {
        val response = ApiConfig.provideApiService().getDetailMovie(movieId, "b2b0bc3afe5894dcc3cee75ef1f7f64f")
        val data = response.body()
        return data!!
    }
}
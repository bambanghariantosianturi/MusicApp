package com.example.movieapplicationv2.data.network

import com.example.movieapplicationv2.model.movie.MovieDetail
import com.example.movieapplicationv2.model.musiclist.MusicEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
interface ApiService {
    @GET("search?")
    suspend fun getMusicTrack(
        @Query("q") search: String
    ): Response<MusicEntity>

//    https://api.themoviedb.org/3/movie/609681?api_key=b2b0bc3afe5894dcc3cee75ef1f7f64f
    @GET("movie/{movieId}")
    suspend fun getDetailMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<MovieDetail>
}
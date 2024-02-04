package com.example.movieapplicationv2.common

/**
 * Created by Alo-BambangHariantoSianturi on 20/11/23.
 */
sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("detail/{movieId}/{movieTitle}") {
        fun createRoute(movieId: Int, movieTitle: String) = "detail/$movieId/$movieTitle"
    }
    object Detail2: Screen("detail2/{movieTitle}") {
        fun createRoute(movieTitle: String) = "detail2/$movieTitle"
    }
}
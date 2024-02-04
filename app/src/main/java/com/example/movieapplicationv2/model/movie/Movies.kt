package com.example.movieapplicationv2.model.movie

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieLists: List<MovieList>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
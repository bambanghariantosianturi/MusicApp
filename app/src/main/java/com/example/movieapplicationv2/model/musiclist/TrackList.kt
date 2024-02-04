package com.example.movieapplicationv2.model.musiclist

data class TrackList(
    val id: Double?,
    val title: String?,
    val preview: String?,
    val artist: Artist,
    val album: Album
) {
    data class Artist(val id: Double?, val name: String?, val picture: String?)
    data class Album(val id: Double?, val title: String?)
}

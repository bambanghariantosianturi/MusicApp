package com.example.movieapplicationv2.utils

import com.example.movieapplicationv2.model.musiclist.MusicEntity
import com.example.movieapplicationv2.model.musiclist.TrackList

object DataDummy {
    fun generateDummyMusicEntity(): MusicEntity {
        val musicList = ArrayList<TrackList>()
        for (i in 0..10) {
            val music = TrackList(
                id = 123.0,
                title = "work",
                preview = "https://cdns-preview-a.dzcdn.net/stream/c-a8f59d5c41501a2a767a088d92946325-1.mp3",
                artist = TrackList.Artist(
                    id = 123.0, name = "Eminem", picture = "https://api.deezer.com/artist/13/image"
                ),
                album = TrackList.Album(id = 123.0, title = "work")
            )
            musicList.add(music)
        }

        return MusicEntity(
            data = musicList,
            total = 600.0,
            next = "go to"
        )
    }
}
package com.itis.androidtestproject.model

import com.itis.androidtestproject.R
import com.itis.androidtestproject.Song

object SongsRepository {
    val songs = arrayListOf(
        Song(
            id = 0,
            artist = "нексюша, гречка",
            title = "Весна",
            raw = R.raw.vesna_neksusha_grechka,
            cover = R.drawable.vesna_cover
        ),
        Song(
            id = 1,
            artist = "Gone.FLUDD",
            title = "Тени Хиросимы",
            raw = R.raw.teni_hirosimi_gonefludd,
            cover = R.drawable.teni_hirosimi_cover
        ),
        Song(
            id = 2,
            artist = "Miyagi & Эндшпиль",
            title = "Silhouette",
            raw = R.raw.silhouette_miyagi_endshpil,
            cover = R.drawable.silhouette_cover
        ),
        Song(
            id = 3,
            artist = "The Neighbourhood",
            title = "Sweater Weather",
            raw = R.raw.sweater_weather_the_neighbourhood,
            cover = R.drawable.sweater_weather_cover
        ),
        Song(
            id = 4,
            artist = "Justin Bieber, Daniel Caesar, Giveon",
            title = "Peaches",
            raw = R.raw.justin_bieber_daniel_caesar_giveon_peaches,
            cover = R.drawable.peaches_cover
        )
    )
    var currentSong = songs[0]
    var currentSongTime = 0

    fun getCurrentSongIndex() = songs.indexOf(currentSong)
    fun getSongIndex(song: Song) = songs.indexOf(song)
}
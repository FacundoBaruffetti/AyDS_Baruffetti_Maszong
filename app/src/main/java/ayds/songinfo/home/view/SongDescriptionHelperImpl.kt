package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release Date: ${
                            this.getPreciseDate(song.releaseDatePrecision,song.releaseDate)
                        }"
            else -> "Song not found"
        }
    }

    private fun getPreciseDate(precision: String, date: String): String =
        when{
            precision == "month" ->
                 date.split("-").take(2).joinToString {"-"}

            precision == "year" ->
                 date.split("-").first()

            else -> date
        }
}
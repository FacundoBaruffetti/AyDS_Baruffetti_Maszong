package ayds.songinfo.home.model.entities

import android.icu.number.Precision

sealed class Song {
    data class SpotifySong(
        val id: String,
        val songName: String,
        val artistName: String,
        val albumName: String,
        val releaseDate: String,
        val spotifyUrl: String,
        val imageUrl: String,
        val releaseDatePrecision: String,
        var isLocallyStored: Boolean = false,
    ) : Song() {
    }

    object EmptySong : Song()
}


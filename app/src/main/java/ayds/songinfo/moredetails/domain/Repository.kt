package ayds.songinfo.moredetails.model

import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import ayds.songinfo.moredetails.domain.LastFMAPI

interface  Repository {
    private val articleDatabase: ArticleDatabase
    private lateinit var lastFMAPI: LastFMAPI

    fun getArtistBiography(artistName: String): ArtistBiography
}



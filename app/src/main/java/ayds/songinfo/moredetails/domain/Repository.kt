package ayds.songinfo.moredetails.model

import ayds.songinfo.moredetails.entities.ArtistBiography

interface  Repository {
    private val articleDatabase: ArticleDatabase
    private lateinit var lastFMAPI: LastFMAPI

    fun getArtistBiography(artistName: String): ArtistBiography
}



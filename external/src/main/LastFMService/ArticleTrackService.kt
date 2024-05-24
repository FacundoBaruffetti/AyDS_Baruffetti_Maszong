package ayds.songinfo.moredetails.data.LastFMService

import ayds.songinfo.moredetails.domain.entities.ArtistBiography

interface ArticleTrackService {

    fun getArticle(artistName: String): ArtistBiography
}
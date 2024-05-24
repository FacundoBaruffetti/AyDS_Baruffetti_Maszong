package ayds.artist.external.LastFMService

import ayds.artist.external.ArtistBiography


interface ArticleTrackService {

    fun getArticle(artistName: String): ArtistBiography
}
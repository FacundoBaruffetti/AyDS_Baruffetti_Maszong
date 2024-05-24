package ayds.artist.external.LastFMService.data

import ayds.artist.external.ExternalArtistBiography


interface ArticleTrackService {

    fun getArticle(artistName: String): ExternalArtistBiography
}
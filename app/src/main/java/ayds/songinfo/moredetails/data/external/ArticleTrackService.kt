package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entities.ArtistBiography

interface ArticleTrackService {

    fun getArticle(artistName: String): ArtistBiography
}
package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entities.Article.ArtistBiography

interface ArticleTrackService {

    fun getArticle(artistName: String): ArtistBiography?
}
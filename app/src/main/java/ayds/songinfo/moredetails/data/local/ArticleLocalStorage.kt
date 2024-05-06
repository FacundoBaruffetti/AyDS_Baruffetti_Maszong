package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.entities.Article.ArtistBiography

interface ArticleLocalStorage {

    fun insertArticle(article: ArtistBiography)

    fun getArticleByArtistName(artistName: String): ArtistBiography?
}
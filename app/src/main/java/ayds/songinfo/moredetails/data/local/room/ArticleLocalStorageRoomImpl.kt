package ayds.songinfo.moredetails.data.local.room

import ayds.songinfo.moredetails.domain.entities.Article.ArtistBiography
import ayds.songinfo.moredetails.data.local.room.LocalStorage

internal class ArticleLocalStorageRoomImpl(
    dataBase: ArticleDatabase,
) : LocalStorage {

    private val articleDao: ArticleDao = dataBase.ArticleDao()

    override fun insertArticle(article: ArtistBiography){
        articleDao.insertArticle(article.toArticleEntity())
    }

    override fun getArticleByArtistName(artistName: String): ArtistBiography? {
        return articleDao.getArticleByArtistName(artistName)?.toArtistBiography()
    }

    private fun ArtistBiography.toArticleEntity() = ArticleEntity(
        this.artistName,
        this.biography,
        this.articleUrl,
    )

    private fun ArticleEntity.toArtistBiography() = ArtistBiography(
        this.artistName,
        this.biography,
        this.articleUrl,
    )
}
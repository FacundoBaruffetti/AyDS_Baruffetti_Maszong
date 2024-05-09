package ayds.songinfo.moredetails.data.local.room

import ayds.songinfo.moredetails.data.local.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.entities.ArtistBiography

internal class ArticleLocalStorageRoomImpl(
    dataBase: ArticleDatabase,
) : ArticleLocalStorage {

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
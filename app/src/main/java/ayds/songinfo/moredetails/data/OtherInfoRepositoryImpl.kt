package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.ArticleTrackService
import ayds.songinfo.moredetails.data.local.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

class OtherInfoRepositoryImpl(
    private val articleLocalStorage: ArticleLocalStorage,
    private val articleTrackService: ArticleTrackService
) : OtherInfoRepository {

    override fun getArtistBiography(artistName: String): ArtistBiography{
        val dbArticle = articleLocalStorage.getArticleByArtistName(artistName)
        val artistBiography: ArtistBiography


        if (dbArticle != null) {
            artistBiography = dbArticle.apply {markItAsLocal()}
        } else {
            artistBiography = articleTrackService.getArticle(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                articleLocalStorage.insertArticle(artistBiography)

            }
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() = copy(isLocallyStored = true)

}
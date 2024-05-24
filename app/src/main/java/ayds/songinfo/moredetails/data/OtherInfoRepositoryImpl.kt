package ayds.songinfo.moredetails.data


import ayds.artist.external.LastFMService.data.ArticleTrackService
import ayds.songinfo.moredetails.data.local.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.artist.external.ExternalArtistBiography

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
            artistBiography = articleTrackService.getArticle(artistName).toArtistBiography()
            if (artistBiography.biography.isNotEmpty()) {
                articleLocalStorage.insertArticle(artistBiography)

            }
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() = copy(isLocallyStored = true)

    private fun ExternalArtistBiography.toArtistBiography() =
        ArtistBiography(this.artistName, this.biography, this.articleUrl, isLocallyStored = false)

}
package ayds.songinfo.moredetails.data.external.tracks

import ayds.songinfo.moredetails.domain.entities.Article.ArtistBiography
import ayds.songinfo.moredetails.data.external.ArticleTrackService
import retrofit2.Response

internal class ArticleTrackServiceImpl(
  private val lastFMAPI: LastFMAPI,
  private val lastFMtoArticleResolver: LastFMtoArticleResolver,
) : ArticleTrackService {

    override fun getArticle(artistName: String): ArtistBiography? {
        var artistBiography = ArtistBiography(artistName, "", "")
        
        try {

            val callResponse = getArticleFromService(artistName)
            artistBiography = lastFMtoArticleResolver.getArtistBioFromExternalData(callResponse.body(), artistName)

        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getArticleFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}
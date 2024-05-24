package ayds.artist.external.LastFMService.data

import ayds.artist.external.ExternalArtistBiography
import java.io.IOException

internal class ArticleTrackServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMtoArticleResolver: LastFMtoArticleResolver,
) : ArticleTrackService {

    override fun getArticle(artistName: String): ExternalArtistBiography {
        lateinit var artistBiography: ExternalArtistBiography

        try {

            val callResponse = getArticleFromService(artistName)
            artistBiography = lastFMtoArticleResolver.map(callResponse.body(), artistName)

        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getArticleFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}
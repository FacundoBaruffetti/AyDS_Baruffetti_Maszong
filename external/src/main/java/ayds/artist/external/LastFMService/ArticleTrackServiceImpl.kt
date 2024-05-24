package ayds.artist.external.LastFMService

import ayds.artist.external.ArtistBiography
import java.io.IOException

class ArticleTrackServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMtoArticleResolver: LastFMtoArticleResolver,
) : ArticleTrackService {

    override fun getArticle(artistName: String): ArtistBiography {
        lateinit var artistBiography: ArtistBiography
        
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
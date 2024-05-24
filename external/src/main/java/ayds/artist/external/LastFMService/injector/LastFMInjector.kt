package ayds.artist.external.LastFMService.injector



import ayds.artist.external.LastFMService.data.JsonToArticleResolver
import ayds.artist.external.LastFMService.data.LastFMAPI
import ayds.artist.external.LastFMService.data.LastFMtoArticleResolver
import ayds.artist.external.LastFMService.data.ArticleTrackService
import ayds.artist.external.LastFMService.data.ArticleTrackServiceImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object LastFMInjector {
    private val lastFMAPI = getlastFMAPI()
    private val lastFMtoArticleResolver: LastFMtoArticleResolver = JsonToArticleResolver()


    val articleTrackService: ArticleTrackService = ArticleTrackServiceImpl(lastFMAPI, lastFMtoArticleResolver)


    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(LASTFM_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun getlastFMAPI() = getRetrofit().create(LastFMAPI::class.java)
}
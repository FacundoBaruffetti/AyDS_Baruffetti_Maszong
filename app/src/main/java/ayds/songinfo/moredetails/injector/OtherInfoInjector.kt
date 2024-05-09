package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room.databaseBuilder
import ayds.songinfo.moredetails.data.external.tracks.JsonToArticleResolver
import ayds.songinfo.moredetails.data.external.tracks.ArticleTrackServiceImpl
import ayds.songinfo.moredetails.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.external.tracks.LastFMAPI
import ayds.songinfo.moredetails.data.local.room.ArticleDatabase
import ayds.songinfo.moredetails.data.local.room.ArticleLocalStorageRoomImpl
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val ARTICLE_BD_NAME = "article-database"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun init(context: Context) {
        val articleDatabase = databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ARTICLE_BD_NAME
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(
                ScalarsConverterFactory.create()
            ).build()

        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMtoArticleResolver = JsonToArticleResolver()

        val articleTrackService = ArticleTrackServiceImpl(lastFMAPI, lastFMtoArticleResolver)

        val articleLocalRoomStorage = ArticleLocalStorageRoomImpl(articleDatabase)

        val repository = OtherInfoRepositoryImpl(articleLocalRoomStorage, articleTrackService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}
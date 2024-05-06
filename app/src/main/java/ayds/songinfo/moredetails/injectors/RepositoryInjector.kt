package ayds.songinfo.moredetails.injectors

import android.content.Context
import androidx.room.Room
import ayds.songinfo.home.model.repository.external.spotify.SpotifyInjector
import ayds.songinfo.moredetails.data.external.spotify.SpotifyTrackService
import ayds.songinfo.moredetails.data.local.ArticleLocalStorage
import ayds.songinfo.moredetails.data.local.room.ArticleDatabase
import ayds.songinfo.moredetails.data.local.room.ArticleLocalStorageRoomImpl
import ayds.songinfo.moredetails.presentation.OtherInfoView
import ayds.songinfo.moredetails.domain.Repository
import ayds.songinfo.moredetails.data.RepositoryImpl


const val ARTICLE_BD_NAME = "article-database"
const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object RepositoryInjector {

    private lateinit var repository: Repository

    fun getRepository(): Repository = repository

    fun initRepository(view: OtherInfoView) {
        val articleDatabase = databaseBuilder(
            view as Context,
            ArticleDatabase::class.java,
            ARTICLE_BD_NAME
        ).build()

        val lastFMAPI = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()
            ).build()

        val articleLocalRoomStorage: ArticleLocalStorage = ArticleLocalStorageRoomImpl(articleDatabase)

        val articleTrackService: ArticleTrackService = SpotifyInjector.articleTrackService

        repository = RepositoryImpl(articleLocalRoomStorage, articleTrackService)
    }
}
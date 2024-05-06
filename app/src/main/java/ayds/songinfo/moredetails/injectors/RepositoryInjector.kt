package ayds.songinfo.home.model

import android.content.Context
import androidx.room.Room
import ayds.songinfo.home.model.repository.SongRepository
import ayds.songinfo.home.model.repository.SongRepositoryImpl
import ayds.songinfo.home.model.repository.external.spotify.SpotifyInjector
import ayds.songinfo.home.model.repository.external.spotify.SpotifyTrackService
import ayds.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage
import ayds.songinfo.home.model.repository.local.spotify.room.SongDatabase
import ayds.songinfo.home.model.repository.local.spotify.room.SpotifyLocalStorageRoomImpl
import ayds.songinfo.home.view.HomeView
import ayds.songinfo.moredetails.domain.Repository


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

        val spotifyLocalRoomStorage: SpotifyLocalStorage = SpotifyLocalStorageRoomImpl(dataBase)

        val spotifyTrackService: SpotifyTrackService = SpotifyInjector.spotifyTrackService

        repository = RepositoryImpl(spotifyLocalRoomStorage, spotifyTrackService)
    }
}
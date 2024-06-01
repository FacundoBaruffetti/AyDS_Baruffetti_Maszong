package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room.databaseBuilder
import ayds.artist.external.LastFMService.injector.LastFMInjector
import ayds.artist.external.newyorktimes.injector.NYTimesInjector
import ayds.artist.external.wikipedia.injector.WikipediaInjector
import ayds.songinfo.moredetails.data.BrokerImpl
import ayds.songinfo.moredetails.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.local.room.CardDatabase
import ayds.songinfo.moredetails.data.local.room.CardLocalStorageRoomImpl
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl

private const val ARTICLE_BD_NAME = "article-database"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun init(context: Context) {
        val cardDatabase = databaseBuilder(
            context,
            CardDatabase::class.java,
            ARTICLE_BD_NAME
        ).build()

        val cardLocalRoomStorage = CardLocalStorageRoomImpl(cardDatabase)

        val broker = BrokerImpl(LastFMInjector.articleTrackService, NYTimesInjector.nyTimesService, WikipediaInjector.wikipediaTrackService)

        val repository = OtherInfoRepositoryImpl(cardLocalRoomStorage, broker)

        val cardDescriptionHelper = CardDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, cardDescriptionHelper)
    }
}
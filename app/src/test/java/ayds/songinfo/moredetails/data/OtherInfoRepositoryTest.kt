package ayds.songinfo.moredetails.data

import ayds.artist.external.LastFMService.data.ArtistBiography
import ayds.artist.external.LastFMService.data.ArticleTrackService
import ayds.songinfo.moredetails.data.local.CardLocalStorage
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.entities.CardSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class OtherInfoRepositoryTest{
    private val LocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val Service: ArticleTrackService = mockk ()
    private val otherInfoRepository: OtherInfoRepository = OtherInfoRepositoryImpl(LocalStorage, Service)

    @Test
    fun `on getArtistBiography call getArticle from local storage`() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM, false)
        every {LocalStorage.getCard("artist")} returns card

        val result = otherInfoRepository.getCard("artist")

        Assert.assertEquals(card, result)
        Assert.assertTrue(result.isLocallyStored)
    }

    @Test
    fun `on getArtistBiography call getArticle from service`() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM, false)
//        val artistBiography = ArtistBiography("artist", "biography", "url")
        every {LocalStorage.getCard("artist")} returns null
        every {Service.getArticle("artist")} returns card

        val result = otherInfoRepository.getCard("artist")

        Assert.assertEquals(card, result)
        Assert.assertFalse(result.isLocallyStored)

        verify { LocalStorage.insertCard(card) }
    }

    @Test
    fun `on empty bio, getArtistBiography call getArticle from service`() {
        val card = Card("artist", "", "url", CardSource.LAST_FM, false)
//        val artistBiography = ArtistBiography("artist", "", "url")
        every {LocalStorage.getCard("artist")} returns null
        every {Service.getArticle("artist")} returns card

        val result = otherInfoRepository.getCard("artist")

        Assert.assertEquals(card, result)
        Assert.assertFalse(result.isLocallyStored)

        verify(inverse = true){ LocalStorage.insertCard(card) }
    }
}
package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.LastFMService.ArticleTrackService
import ayds.songinfo.moredetails.data.local.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class OtherInfoRepositoryTest{
    private val LocalStorage: ArticleLocalStorage = mockk(relaxUnitFun = true)
    private val Service: ArticleTrackService = mockk ()
    private val otherInfoRepository: OtherInfoRepository = OtherInfoRepositoryImpl(LocalStorage, Service)

    @Test
    fun `on getArtistBiography call getArticle from local storage`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", false)
        every {LocalStorage.getArticleByArtistName("artist")} returns artistBiography

        val result = otherInfoRepository.getArtistBiography("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertTrue(result.isLocallyStored)
    }

    @Test
    fun `on getArtistBiography call getArticle from service`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", false)
        every {LocalStorage.getArticleByArtistName("artist")} returns null
        every {Service.getArticle("artist")} returns artistBiography

        val result = otherInfoRepository.getArtistBiography("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)

        verify { LocalStorage.insertArticle(artistBiography) }
    }

    @Test
    fun `on empty bio, getArtistBiography call getArticle from service`() {
        val artistBiography = ArtistBiography("artist", "", "url", false)
        every {LocalStorage.getArticleByArtistName("artist")} returns null
        every {Service.getArticle("artist")} returns artistBiography

        val result = otherInfoRepository.getArtistBiography("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)

        verify(inverse = true){ LocalStorage.insertArticle(artistBiography) }
    }
}
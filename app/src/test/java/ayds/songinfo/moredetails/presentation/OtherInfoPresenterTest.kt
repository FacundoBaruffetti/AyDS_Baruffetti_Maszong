package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.entities.CardSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest{
    private val repository: OtherInfoRepository = mockk()
    private val artistBiographyDescriptionHelper: CardDescriptionHelper = mockk()
    private val otherInfoPresenter: OtherInfoPresenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)

    @Test
    fun `should return Artist Bio UIState`() {
        val card = Card("artistName", "biography", "articleUrl", CardSource.LAST_FM, false)
        every { repository.getCard("artistName") } returns card
        every { artistBiographyDescriptionHelper.getDescription(card) } returns "description"

        val artistBiographyTester: (CardUiState) -> Unit = mockk(relaxed = true)

        otherInfoPresenter.cardObservable.subscribe(artistBiographyTester)
        otherInfoPresenter.getArtistInfo("artistName")

        val result = CardUiState("artistName", "description", "articleUrl")
        verify{ artistBiographyTester(result)}

    }




}
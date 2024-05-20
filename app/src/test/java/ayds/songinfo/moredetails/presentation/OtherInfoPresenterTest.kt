package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest{
    private val repository: OtherInfoRepository = mockk()
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper = mockk()
    private val otherInfoPresenter: OtherInfoPresenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)

    @Test
    fun `should return Artist Bio UIState`() {
        val artistBio = ArtistBiography("artistName", "biography", "articleUrl")
        every { repository.getArtistBiography("artistName") } returns artistBio
        every { artistBiographyDescriptionHelper.getDescription(artistBio) } returns "description"

        val artistBiographyTester: (ArtistBiographyUiState) -> Unit = mockk(relaxed = true)

        otherInfoPresenter.artistBiographyObservable.subscribe(artistBiographyTester)
        otherInfoPresenter.getArtistInfo("artistName")

        val result = ArtistBiographyUiState("artistName", "description", "articleUrl")
        verify{ artistBiographyTester(result)}

    }




}
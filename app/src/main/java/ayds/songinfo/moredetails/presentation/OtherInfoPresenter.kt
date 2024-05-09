package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entities.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val artistBiographyObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper
) : OtherInfoPresenter {

    override val artistBiographyObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String){
        val artistBiography = repository.getArtistBiography(artistName)

        artistBiographyObservable.notify(artistBiography.toUiState())
    }

    private fun ArtistBiography.toUiState() = ArtistBiographyUiState(
        artistName,
        artistBiographyDescriptionHelper.getDescription(this),
        articleUrl
    )
}

package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val cardObservable: Observable<CardUiState>
    fun getArtistInfo(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
) : OtherInfoPresenter {

    override val cardObservable = Subject<CardUiState>()

    override fun getArtistInfo(artistName: String){
        val card = repository.getCard(artistName)

        cardObservable.notify(card.toUiState())
    }

    private fun Card.toUiState() = CardUiState(
        artistName,
        cardDescriptionHelper.getDescription(this),
        infoUrl
    )
}

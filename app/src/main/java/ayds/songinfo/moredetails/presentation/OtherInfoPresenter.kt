package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.domain.entities.CardSource

interface OtherInfoPresenter {
    val cardObservable: Observable<List<CardUiState>>
    fun getArtistInfo(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
) : OtherInfoPresenter {

    override val cardObservable = Subject<List<CardUiState>>()

    override fun getArtistInfo(artistName: String){
        val cardList = repository.getCard(artistName)

        cardObservable.notify(cardList.map { card: Card -> card.toUiState() })
    }

    private fun Card.toUiState(): CardUiState {

        val logoUrl: String = when (source) {
            CardSource.LAST_FM -> LASTFM_LOGO_URL
            CardSource.NY_TIMES -> NYT_LOGO_URL
            CardSource.WIKIPEDIA -> WIKIPEDIA_LOGO_URL
        }

        return CardUiState(
            artistName,
            cardDescriptionHelper.getDescription(this),
            url,
            logoUrl
        )
    }
}

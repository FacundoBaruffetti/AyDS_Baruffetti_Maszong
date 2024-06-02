package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.local.CardLocalStorage
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository

class OtherInfoRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
    ) : OtherInfoRepository {

    override fun getCard(artistName: String): List<Card>{
        val dbCard = cardLocalStorage.getCard(artistName)
        var cardList: MutableList<Card> = mutableListOf()


        if (dbCard != null) {
            cardList.add( dbCard.apply {markItAsLocal()} )
        } else {
            cardList = broker.getServices(artistName).toMutableList()
            if (cardList.isNotEmpty()) {
                cardLocalStorage.insertCard(cardList.first())

            }
        }
        return cardList
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}
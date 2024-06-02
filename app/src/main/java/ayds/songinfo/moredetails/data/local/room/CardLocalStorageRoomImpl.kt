package ayds.songinfo.moredetails.data.local.room

import ayds.songinfo.moredetails.data.local.CardLocalStorage
import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.entities.CardSource

internal class CardLocalStorageRoomImpl(
    dataBase: CardDatabase,
) : CardLocalStorage {

    private val cardDao: CardDao = dataBase.CardDao()

    override fun insertCard(card: Card){
        cardDao.insertCard(card.toCardEntity())
    }

    override fun getCard(artistName: String): Card? {
        return cardDao.getCard(artistName)?.toCard()
    }

    private fun Card.toCardEntity() = CardEntity(
        this.artistName,
        this.content,
        this.url,
        this.source.ordinal,
    )

    private fun CardEntity.toCard() = Card(
        this.artistName,
        this.content,
        this.infoUrl,
        CardSource.entries[this.source]
    )
}
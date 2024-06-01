package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.entities.Card

interface CardLocalStorage {

    fun insertCard(article: Card)

    fun getCard(artistName: String): Card?
}
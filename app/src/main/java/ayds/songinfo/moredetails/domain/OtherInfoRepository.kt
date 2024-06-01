package ayds.songinfo.moredetails.domain

import ayds.songinfo.moredetails.domain.entities.Card

interface  OtherInfoRepository {
    fun getCard(artistName: String): List<Card>
}



package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.entities.CardSource
import ayds.artist.external.LastFMService.data.ArtistBiography
import ayds.artist.external.LastFMService.data.ArticleTrackService
import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.artist.external.wikipedia.data.WikipediaTrackService


interface Broker {
    fun getServices(artistName: String): List<Card>
}

internal class BrokerImpl(
    private val articleTrackService: ArticleTrackService,
    private val nYTimesService: NYTimesService,
    private val wikipediaTrackService: WikipediaTrackService
) : Broker {

    override fun getServices(artistName: String): List<Card> {
        val cardList: MutableList<Card> = mutableListOf()

        val article: Card = getLastFMArticle(artistName)
        val nyTimes: Card? = getNYTimesArticle(artistName)
        val wikipedia: Card? = getWikipediaArticle(artistName)

        if(article.content != "")
            cardList.add(article)

        if(nyTimes != null && nyTimes.content != "")
            cardList.add(nyTimes)

        if(wikipedia != null)
            cardList.add(wikipedia)

        return cardList
    }

    private fun getLastFMArticle(artistName: String): Card =
        articleTrackService.getArticle(artistName).toCard()

    private fun getNYTimesArticle(artistName: String): Card? =
        nYTimesService.getArtistInfo(artistName).toCard()

    private fun getWikipediaArticle(artistName: String): Card? =
        wikipediaTrackService.getInfo(artistName)?.toCard(artistName)


    private fun ArtistBiography.toCard() =
        Card(
            this.artistName,
            this.biography ?: "",
            this.articleUrl,
            CardSource.LAST_FM
        )

    private fun NYTimesArticle.toCard(): Card? {
        var card: Card? = null

        if(this is NYTimesArticle.NYTimesArticleWithData)
            card = Card(
                this.name?:"",
                this.info?:"",
                this.url,
                CardSource.NY_TIMES
            )

        return card
    }

    private fun WikipediaArticle.toCard(artistName: String) =
        Card(
            artistName,
            this.description,
            this.wikipediaURL,
            CardSource.WIKIPEDIA
        )

}
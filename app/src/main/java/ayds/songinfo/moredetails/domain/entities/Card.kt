package ayds.songinfo.moredetails.domain.entities

data class Card(
    val artistName: String,
    val content: String,
    val url: String,
    val source: CardSource,
    var isLocallyStored: Boolean = false
)

enum class CardSource{
    LAST_FM,
    NY_TIMES,
    WIKIPEDIA
}

package ayds.songinfo.moredetails.domain.entities

data class Card(
    val artistName: String,
    val content: String,
    val infoUrl: String,
    val source: CardSource,
    var isLocallyStored: Boolean = false,
    val logoUrl: String = ""
)

enum class CardSource{
    LAST_FM,
    NY_TIMES,
    WIKIPEDIA
}

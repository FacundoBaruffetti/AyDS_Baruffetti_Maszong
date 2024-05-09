package ayds.songinfo.moredetails.domain.entities

data class ArtistBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    var isLocallyStored: Boolean = false
)

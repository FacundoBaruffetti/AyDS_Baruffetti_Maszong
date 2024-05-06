sealed class Article {
    data class ArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String,
        //var isLocallyStored: Boolean = false,
    ) : Article() {
    }

    object EmptyBiography : Article()
}
sealed class ArtistBiography {
    data class SpotifyArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String,
    ) : ArtistBiography() {
    }

    object EmptyBiography : ArtistBiography()
}
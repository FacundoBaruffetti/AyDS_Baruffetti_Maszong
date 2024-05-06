package ayds.songinfo.home.moredetails.repository

import ayds.songinfo.home.moredetails.domain.entities
import ayds.songinfo.home.moredetails.data.external.spotify.SpotifyTrackService
import ayds.songinfo.home.moredetails.data.local.spotify.SpotifyLocalStorage


internal class RepositoryImpl(
    private val articleLocalStorage: ArticleLocalStorage,
    private val articleTrackService: ArticleTrackService
) : Repository {

    override fun getArtistBiography(): ArtistBiography{
        val artistName = getArtistName()
        val artistBiography = articleLocalStorage.getArticleByArtistName(artistName)

        if (artistBiography != null) {
            artistBiography = artistBiography.markItAsLocal()
        } else {
            try {
                artistBiography = articleTrackService.getArticle(artistName)
                if (artistBiography.biography.isNotEmpty()) {
                    articleLocalStorage.insertArticle(artistBiography)

                } catch (e: Exception) {
                    artistBiography = null
                }
            }
        }
        return artistBiography ?: EmptyBiography
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun ArtistBiography.markItAsLocal() = copy(biography = "[*]$biography")

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}
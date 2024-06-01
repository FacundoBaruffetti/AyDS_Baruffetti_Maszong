package ayds.artist.external.LastFMService.data


interface ArticleTrackService {

    fun getArticle(artistName: String): ArtistBiography
}
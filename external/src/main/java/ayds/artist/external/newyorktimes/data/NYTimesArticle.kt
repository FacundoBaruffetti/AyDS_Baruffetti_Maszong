package ayds.artist.external.newyorktimes.data

sealed class NYTimesArticle {

    data class NYTimesArticleWithData(
        val name: String?,
        val info: String?,
        val url: String,
    ): NYTimesArticle()

    object EmptyArtistDataExternal : NYTimesArticle()

}
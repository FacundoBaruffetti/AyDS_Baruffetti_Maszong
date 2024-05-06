package ayds.songinfo.home.model.repository.external.spotify.tracks

import com.google.gson.Gson
import ayds.songinfo.moredetails.domain.entities.Article.ArtistBiography
import com.google.gson.JsonObject

interface lastFMtoArticleResolver {
    fun getArtistBioFromExternalData(serviceData: String?, artistName: String): ArtistBiography?
}


private const val ARTISTS = "artists"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"


internal class JsonToArticleResolver : lastFMtoArticleResolver {

    override fun getArtistBioFromExternalData( serviceData: String?, artistName: String ): ArtistBiography? =
        try {
            serviceData?.getFirstItem()?.let { artist ->
                ArtistBiography(
                  artistName, artist.getBiographyText(), artist.getArticleUrl()
                )
            }
        } catch (e: Exception) {
            null
        }

    private fun String?.getFirstItem(): JsonObject {
        val jobj = Gson().fromJson(serviceData, JsonObject::class.java)
        val artists = jobj[ARTISTS].getAsJsonArray()
        return artists[0].asJsonObject
    }

    private fun JsonObject.getBiographyText(): String {
        val bio = this[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        return extract?.asString ?: "No Results"
    }

    private fun JsonObject.getArticleUrl(): String {
        val url = this[URL]
        return url.asString
    }
}
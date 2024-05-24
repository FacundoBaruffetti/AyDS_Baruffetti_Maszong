package ayds.artist.external.LastFMService


import ayds.artist.external.ArtistBiography
import com.google.gson.Gson
import com.google.gson.JsonObject

interface LastFMtoArticleResolver {
    fun map(serviceData: String?, artistName: String): ArtistBiography
}


private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"

class JsonToArticleResolver : LastFMtoArticleResolver {

    override fun map( serviceData: String?, artistName: String ): ArtistBiography {

        val artist = getArtist(serviceData)

        return ArtistBiography (artistName, artist.getBiographyText(), artist.getArticleUrl())
        }

    private fun getArtist(serviceData: String?): JsonObject {
        val jobj = Gson().fromJson(serviceData, JsonObject::class.java)
        val artist = jobj[ARTIST].getAsJsonObject()
        return artist
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
package ayds.songinfo.moredetails.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale
import ayds.songinfo.moredetails.model.OtherInfoPresenter

const val LASTFM_IMAGE_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

interface OtherInfoView {

    fun setPresenter(presenter: OtherInfoPresenter)
}

class OtherInfoViewActivity : Activity(), OtherInfoView {

    private lateinit var presenter: OtherInfoPresenter

    override fun setPresenter(presenter: OtherInfoPresenter) {
        this.presenter = presenter
        presenter.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<HomeUiEvent> =
        Observer { value ->
            when (value) {
                HomeUiEvent.Search -> searchSong()
                HomeUiEvent.MoreDetails -> moreDetails()
                is HomeUiEvent.OpenSongUrl -> openSongUrl()
            }
        }

    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        getArtistInfo()
    }

    private fun initModule() {
        OtherInfoViewInjector.init(this)
        setPresenter(OtherInfoPresenterInjector.getOtherInfoPresenter())
    }

    private fun initProperties() {
        articleTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton)
        lastFMImageView = findViewById(R.id.lastFMImageView)
    }

    private fun getArtistInfo(){
        onActionSubject.notify(UiEvent.getArtistInfo)
    }



    private fun updateUi(artistBiography: ArtistBiography) {
        runOnUiThread {
            updateOpenUrlButton(artistBiography)
            updateLastFMLogo()
            updateArticleText(artistBiography)
        }
    }

    private fun updateOpenUrlButton(artistBiography: ArtistBiography) {
        openUrlButton.setOnClickListener {
            navigateToUrl(artistBiography.articleUrl)
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLastFMLogo() {
        Picasso.get().load(LASTFM_IMAGE_URL).into(lastFMImageView)
    }

    private fun updateArticleText(artistBiography: ArtistBiography) {
        val text = artistBiography.biography.replace("\\n", "\n")
        articleTextView.text = Html.fromHtml(textToHtml(text, artistBiography.artistName))
    }

//    private fun textToHtml(text: String, term: String?): String {
//        val builder = StringBuilder()
//        builder.append("<html><div width=400>")
//        builder.append("<font face=\"arial\">")
//        val textWithBold = text
//            .replace("'", " ")
//            .replace("\n", "<br>")
//            .replace(
//                "(?i)$term".toRegex(),
//                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
//            )
//        builder.append(textWithBold)
//        builder.append("</font></div></html>")
//        return builder.toString()
//    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}

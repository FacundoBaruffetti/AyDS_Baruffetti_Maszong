package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import com.squareup.picasso.Picasso
import ayds.songinfo.moredetails.injector.OtherInfoInjector

class OtherInfoViewActivity : Activity() {

    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView

    private lateinit var presenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initPresenter()
        observePresenter()
        getArtistInfoAsync()
    }

    private fun initPresenter() {
        OtherInfoInjector.init(this)
        presenter = OtherInfoInjector.presenter
    }

    private fun observePresenter() {
        presenter.artistBiographyObservable.subscribe { artistBiography ->
            updateUi(artistBiography)
        }
    }

    private fun initProperties() {
        articleTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton)
        lastFMImageView = findViewById(R.id.lastFMImageView)
    }

    private fun getArtistInfoAsync(){
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo(){
        presenter.getArtistInfo(getArtistName())
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun updateUi(uiState: ArtistBiographyUiState) {
        runOnUiThread {
            updateOpenUrlButton(uiState.articleUrl)
            updateLastFMLogo(uiState.imageUrl)
            updateArticleText(uiState.infoHtml)
        }
    }

    private fun updateOpenUrlButton(articleUrl: String) {
        openUrlButton.setOnClickListener {
            navigateToUrl(articleUrl)
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLastFMLogo(imageUrl: String) {
        Picasso.get().load(imageUrl).into(lastFMImageView)
    }

    private fun updateArticleText(infoHtml: String) {
        articleTextView.text = Html.fromHtml(infoHtml)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}

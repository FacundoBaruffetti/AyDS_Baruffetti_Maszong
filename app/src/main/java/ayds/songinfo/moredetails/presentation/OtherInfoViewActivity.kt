package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import com.squareup.picasso.Picasso
import ayds.songinfo.moredetails.injector.OtherInfoInjector

class OtherInfoViewActivity : Activity() {

    private lateinit var cardContent1TextView: TextView
    private lateinit var openUrl1Button: Button
    private lateinit var source1ImageView: ImageView

    private lateinit var cardContent2TextView: TextView
    private lateinit var openUrl2Button: Button
    private lateinit var source2ImageView: ImageView

    private lateinit var cardContent3TextView: TextView
    private lateinit var openUrl3Button: Button
    private lateinit var source3ImageView: ImageView

    private lateinit var presenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initPresenter()
        observePresenter()
        getArtistCardAsync()
    }

    private fun initPresenter() {
        OtherInfoInjector.init(this)
        presenter = OtherInfoInjector.presenter
    }

    private fun observePresenter() {
        presenter.cardObservable.subscribe { card ->
            updateUi(card)
        }
    }

    private fun initProperties() {
        cardContent1TextView = findViewById(R.id.cardContent1TextView)
        openUrl1Button = findViewById(R.id.openUrl1Button)
        source1ImageView = findViewById(R.id.source1ImageView)

        cardContent2TextView = findViewById(R.id.cardContent2TextView)
        openUrl2Button = findViewById(R.id.openUrl2Button)
        source2ImageView = findViewById(R.id.source2ImageView)

        cardContent3TextView = findViewById(R.id.cardContent3TextView)
        openUrl3Button = findViewById(R.id.openUrl3Button)
        source3ImageView = findViewById(R.id.source3ImageView)
    }

    private fun getArtistCardAsync(){
        Thread {
            getArtistCard()
        }.start()
    }

    private fun getArtistCard(){
        presenter.getArtistInfo(getArtistName())
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun updateUi(uiStateList: List<CardUiState>) {
        runOnUiThread {
            openUrl1Button.updateOpenUrlButton (uiStateList[0].url)
            source1ImageView.updateLogo(uiStateList[0].imageUrl)
            cardContent1TextView.updateText(uiStateList[0].contentHtml)

            if(uiStateList.size==2) {
                openUrl2Button.updateOpenUrlButton(uiStateList[1].url)
                source2ImageView.updateLogo(uiStateList[1].imageUrl)
                cardContent2TextView.updateText(uiStateList[1].contentHtml)
            }

            if(uiStateList.size==3) {
                openUrl3Button.updateOpenUrlButton(uiStateList[2].url)
                source3ImageView.updateLogo(uiStateList[2].imageUrl)
                cardContent3TextView.updateText(uiStateList[2].contentHtml)
            }
        }
    }

    private fun Button.updateOpenUrlButton(url: String) {
        this.setOnClickListener {
            navigateToUrl(url)
        }
        this.visibility = View.VISIBLE
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun ImageView.updateLogo(imageUrl: String) {
        Picasso.get().load(imageUrl).into(this)
    }

    private fun TextView.updateText(infoHtml: String) {
        this.text = Html.fromHtml(infoHtml)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}

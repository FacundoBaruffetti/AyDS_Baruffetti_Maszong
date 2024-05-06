package ayds.songinfo.moredetails.presenter

import ayds.observer.Observer
import ayds.songinfo.moredetails.model.OtherInfoModel
import ayds.songinfo.moredetails.view.UiEvent
import ayds.songinfo.moredetails.view.OtherInfoView
import ayds.songinfo.moredetails.domain
import jdk.jfr.internal.Repository




interface OtherInfoPresenter {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState
}

internal class OtherInfoPresenterImpl(
    private val repository: Repository
) : ayds.songinfo.home.controller.HomeController {

    private val onActionSubject = Subject<MoreDetailsUiState>()

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                UiEvent.GetArtistInfo -> getArtistInfo()
            }
        }

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()


    private fun getArtistInfo(){
        Thread {
            val artistBiography = repository.getArtistBiography(uiState.artistName)
            updateUi(artistBiography)
        }.start()
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

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }
}

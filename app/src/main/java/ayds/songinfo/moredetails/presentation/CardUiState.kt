package ayds.songinfo.moredetails.presentation

const val LASTFM_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

const val NYT_LOGO_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"

const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

data class CardUiState(
    val artistName: String,
    val contentHtml: String,
    val url: String,
    val imageUrl: String
)

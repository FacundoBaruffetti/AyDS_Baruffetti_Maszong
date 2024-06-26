package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.entities.Card
import java.util.Locale

interface CardDescriptionHelper {
    fun getDescription(card: Card): String
}

private const val HEADER = "<html><div width=400><font face=\"arial\">"
private const val FOOTER = "</font></div></html>"
private const val PREFIX = "[*]"

internal class CardDescriptionHelperImpl : CardDescriptionHelper {

    override fun getDescription(card: Card): String {
        val text = getTextBiography(card)
        return textToHtml(text, card.artistName)
    }

    private fun getTextBiography(card: Card): String {
        val prefix = if (card.isLocallyStored) PREFIX else ""
        val text = card.content
        return "$prefix$text"
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append(HEADER)
        val textWithBold = text
            .replace("\\n", "\n")
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append(FOOTER)
        return builder.toString()
    }
}

package ayds.songinfo.moredetails.presentation


import ayds.songinfo.moredetails.domain.entities.Card
import ayds.songinfo.moredetails.domain.entities.CardSource

import org.junit.Assert
import org.junit.Test


class ArtistBiographyDescriptionHelperTest{

    private val cardDescriptionHelper: CardDescriptionHelper =
        CardDescriptionHelperImpl()

    @Test
    fun `on local stored artist should return biography`() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM, true)

        val result = cardDescriptionHelper.getDescription(card)
        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">[*]biography</font></div></html>",
            result)
    }

    @Test
    fun `on no local stored artist should return biography`() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM, false)

        val result = cardDescriptionHelper.getDescription(card)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography</font></div></html>",
            result)
    }

    @Test
    fun `should remove apostrophes`() {
        val card = Card("artist", "biography'n", "url", CardSource.LAST_FM, false)

        val result = cardDescriptionHelper.getDescription(card)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography n</font></div></html>",
            result
        )
    }

    @Test
    fun `should fix on double slash`() {
        val card = Card("artist", "biography\\n", "url", CardSource.LAST_FM, false)

        val result = cardDescriptionHelper.getDescription(card)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }

    @Test
    fun `should map break lines`() {
        val card = Card("artist", "biography\n", "url", CardSource.LAST_FM, false)

        val result = cardDescriptionHelper.getDescription(card)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }
    @Test
    fun `should set artist name bold`() {
        val card = Card("artist", "biography artist", "url", CardSource.LAST_FM, false)

        val result = cardDescriptionHelper.getDescription(card)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography <b>ARTIST</b></font></div></html>",
            result
        )
    }

}

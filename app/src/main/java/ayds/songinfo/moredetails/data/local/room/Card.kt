package ayds.songinfo.moredetails.data.local.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey
    val artistName: String,
    val description: String,
    val infoUrl: String,
    val source: String,
    val sourceLogoUrl: String
)
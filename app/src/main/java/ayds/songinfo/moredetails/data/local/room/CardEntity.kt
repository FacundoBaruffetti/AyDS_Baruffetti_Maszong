package ayds.songinfo.moredetails.data.local.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardEntity(
    @PrimaryKey
    val artistName: String,
    val content: String,
    val infoUrl: String,
    val source: Int,
)
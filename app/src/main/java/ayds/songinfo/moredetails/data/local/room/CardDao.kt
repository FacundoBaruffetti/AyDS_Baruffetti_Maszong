package ayds.songinfo.moredetails.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(article: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName LIMIT 1")
    fun getCard(artistName: String): CardEntity?

}
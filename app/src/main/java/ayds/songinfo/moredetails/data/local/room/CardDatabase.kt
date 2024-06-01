package ayds.songinfo.moredetails.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}
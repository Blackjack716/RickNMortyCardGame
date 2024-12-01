package com.rnm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rnm.data.local.dao.CardDao
import com.rnm.data.local.model.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CardDao() : CardDao

    companion object {
        const val DATABASE_NAME = "RickNMortyCardGame-database"
    }
}
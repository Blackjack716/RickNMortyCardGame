package com.quotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quotes.data.local.dao.CardDao
import com.quotes.data.local.model.CardEntity

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
package com.rnm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rnm.data.local.model.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM CardEntity WHERE isFav = 1")
    fun getFavouriteCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM CardEntity")
    fun getAllCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM CardEntity WHERE id = :cardId")
    fun getCard(cardId: Int): Flow<CardEntity>

    @Update
    fun updateCard(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCards(cards: List<CardEntity>)

    @Delete
    fun delete(cardEntity: CardEntity)
}
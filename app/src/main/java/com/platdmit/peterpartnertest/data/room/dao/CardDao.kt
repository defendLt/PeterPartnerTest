package com.platdmit.peterpartnertest.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.peterpartnertest.data.room.entity.DbCard

@Dao
interface CardDao : BaseDao<DbCard> {
    @Query("SELECT * FROM dbcard LIMIT 1")
    fun getCard(): DbCard?

    @Query("SELECT * FROM dbcard WHERE cardNumber = :number")
    fun getCard(number: String): DbCard?

    @Query("SELECT * FROM dbcard")
    fun getCards(): List<DbCard>?

    @Query("DELETE FROM dbcard")
    fun delCards()
}
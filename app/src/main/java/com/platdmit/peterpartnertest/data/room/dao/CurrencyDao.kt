package com.platdmit.peterpartnertest.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.peterpartnertest.data.room.entity.DbCurrency

@Dao
interface CurrencyDao : BaseDao<DbCurrency> {
    @Query("SELECT * FROM dbcurrency")
    fun getCurrencies(): List<DbCurrency>?

    @Query("SELECT * FROM dbcurrency WHERE charCode = :charCode")
    fun getCurrency(charCode : String): DbCurrency?

    @Query("DELETE FROM dbcurrency")
    fun delCurrencies()
}
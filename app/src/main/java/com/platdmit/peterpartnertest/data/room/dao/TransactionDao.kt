package com.platdmit.peterpartnertest.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.peterpartnertest.data.room.entity.DbTransaction

@Dao
interface TransactionDao : BaseDao<DbTransaction> {
    @Query("SELECT * FROM dbtransaction WHERE cardNumber = :cartNumber")
    fun getTransactions(cartNumber: String) : List<DbTransaction>?
}
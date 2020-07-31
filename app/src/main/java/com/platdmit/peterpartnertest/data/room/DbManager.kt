package com.platdmit.peterpartnertest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.platdmit.peterpartnertest.data.room.dao.CardDao
import com.platdmit.peterpartnertest.data.room.dao.CurrencyDao
import com.platdmit.peterpartnertest.data.room.dao.TransactionDao
import com.platdmit.peterpartnertest.data.room.entity.DbCard
import com.platdmit.peterpartnertest.data.room.entity.DbCurrency
import com.platdmit.peterpartnertest.data.room.entity.DbTransaction

@Database(entities = [DbCard::class, DbCurrency::class, DbTransaction::class],
    version = 1, exportSchema = false)
abstract class DbManager : RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun transactionDao(): TransactionDao

    companion object{
        const val DATABASE_NAME = "pp_test"
    }
}
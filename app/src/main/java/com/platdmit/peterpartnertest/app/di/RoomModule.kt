package com.platdmit.peterpartnertest.app.di

import android.content.Context
import androidx.room.Room
import com.platdmit.peterpartnertest.data.room.DbManager
import com.platdmit.peterpartnertest.data.room.dao.CardDao
import com.platdmit.peterpartnertest.data.room.dao.CurrencyDao
import com.platdmit.peterpartnertest.data.room.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun providePPTb(@ApplicationContext context: Context): DbManager {
        return Room
            .databaseBuilder(
                context,
                DbManager::class.java,
                DbManager.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCardDao( dbManager: DbManager ) : CardDao {
        return dbManager.cardDao()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao( dbManager: DbManager ) : CurrencyDao {
        return dbManager.currencyDao()
    }

    @Singleton
    @Provides
    fun provideTransactionDao( dbManager: DbManager ) : TransactionDao {
        return dbManager.transactionDao()
    }
}
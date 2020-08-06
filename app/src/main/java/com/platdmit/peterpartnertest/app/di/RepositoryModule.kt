package com.platdmit.peterpartnertest.app.di

import com.platdmit.peterpartnertest.data.CardRepoImpl
import com.platdmit.peterpartnertest.data.CurrencyRepoImpl
import com.platdmit.peterpartnertest.data.converters.CardConverterImpl
import com.platdmit.peterpartnertest.data.converters.CurrencyConverterImpl
import com.platdmit.peterpartnertest.data.converters.TransactionConverterImpl
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrency
import com.platdmit.peterpartnertest.data.room.dao.CardDao
import com.platdmit.peterpartnertest.data.room.dao.CurrencyDao
import com.platdmit.peterpartnertest.data.room.dao.TransactionDao
import com.platdmit.peterpartnertest.domain.repositories.CardRepo
import com.platdmit.peterpartnertest.domain.repositories.CurrencyRepo
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCardRepo
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCurrencyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCardRepo(
        apiCardRepo: ApiCardRepo<ApiCard>,
        cardDao: CardDao,
        transactionDao: TransactionDao,
        cardConverter: CardConverterImpl,
        transactionConverter: TransactionConverterImpl
    ): CardRepo {
        return CardRepoImpl(
            apiCardRepo,
            cardDao,
            transactionDao,
            cardConverter,
            transactionConverter
        )
    }

    @Provides
    @Singleton
    fun provideCurrencyRepo(
        apiCurrencyRepo: ApiCurrencyRepo<ApiCurrency>,
        dbCurrencyRepo: CurrencyDao,
        currencyConverter: CurrencyConverterImpl
    ): CurrencyRepo {
        return CurrencyRepoImpl(
            apiCurrencyRepo,
            dbCurrencyRepo,
            currencyConverter
        )
    }
}
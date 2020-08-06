package com.platdmit.peterpartnertest.app.di

import com.platdmit.peterpartnertest.domain.repositories.CardRepo
import com.platdmit.peterpartnertest.domain.repositories.CurrencyRepo
import com.platdmit.peterpartnertest.domain.usecase.CardUseCase
import com.platdmit.peterpartnertest.domain.usecase.CardsInteractor
import com.platdmit.peterpartnertest.domain.usecase.CardsUseCase
import com.platdmit.peterpartnertest.domain.usecase.MainInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetCardsUseCase(
        cardRepo: CardRepo
    ) : CardsUseCase {
        return CardsInteractor(
            cardRepo
        )
    }

    @Singleton
    @Provides
    fun provideGetCardUseCase(
        cardRepo: CardRepo,
        currencyRepo: CurrencyRepo
    ) : CardUseCase {
        return MainInteractor(
            cardRepo,
            currencyRepo
        )
    }
}
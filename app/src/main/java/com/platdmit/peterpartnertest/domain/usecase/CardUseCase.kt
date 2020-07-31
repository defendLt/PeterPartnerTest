package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CardUseCase {
    fun getStartCard(): Single<Card>
    fun getCardByNumber(cardNumber: String): Single<Card>
    fun getCardForCurrency(card: Card, currencyType: CurrencyType): Single<Card>
    fun refreshCurrency(): Completable
}
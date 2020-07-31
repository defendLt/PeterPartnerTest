package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import io.reactivex.rxjava3.core.Single

interface CardUseCase {
    fun getStartCard(): Single<Card>
    fun getCardByNumber(cardNumber: String): Single<Card>
    fun getCardForCurrency(card: Card, currencyType: CurrencyType): Single<Card>
}
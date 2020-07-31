package com.platdmit.peterpartnertest.domain.repositories

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Currency
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CurrencyRepo {
    fun getCurrencies() : Single<List<Currency>>
    fun getCurrency(currencyType: CurrencyType) : Single<Currency>
    fun refreshCurrency() : Completable
}
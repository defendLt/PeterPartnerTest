package com.platdmit.peterpartnertest.data

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrency
import com.platdmit.peterpartnertest.data.room.dao.CurrencyDao
import com.platdmit.peterpartnertest.data.room.entity.DbCurrency
import com.platdmit.peterpartnertest.domain.converters.CurrencyConverter
import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Currency
import com.platdmit.peterpartnertest.domain.repositories.CurrencyRepo
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCurrencyRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CurrencyRepoImpl(
    private val apiCurrencyRepo: ApiCurrencyRepo<ApiCurrency>,
    private val dbCurrencyRepo: CurrencyDao,
    private val currencyConverter: CurrencyConverter<ApiCurrency, DbCurrency, Currency>
) : CurrencyRepo {
    override fun getCurrencies(): Single<List<Currency>> {
        return Single.create<List<Currency>>{ emitter ->
            val currencies = dbCurrencyRepo.getCurrencies()?.map { currencyConverter.fromDbToDomain(it) }
            if(currencies != null){
                emitter.onSuccess(currencies)
            } else {
                emitter.onError(Throwable("Empty"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getCurrency(currencyType: CurrencyType): Single<Currency> {
        return Single.create<Currency>{ emitter ->
            val currency = dbCurrencyRepo.getCurrency(currencyType.name)?.let{
                currencyConverter.fromDbToDomain(it)
            }
            if(currency != null){
                emitter.onSuccess(currency)
            } else {
                emitter.onError(Throwable("Empty"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun refreshCurrency(): Completable {
        return Completable.create{success ->
            apiCurrencyRepo.getCurrency().onErrorComplete {
                success.onError(Throwable("Fall"))
                true
            }.subscribe({ apiCurrency ->
                dbCurrencyRepo.delCurrencies()
                val dbCurrency = apiCurrency.map { currencyConverter.fromApiToDb(it) }
                dbCurrencyRepo.insertList(dbCurrency)
                success.onComplete()
            }, {
                success.onError(it)
            })
        }.subscribeOn(Schedulers.io())
    }
}
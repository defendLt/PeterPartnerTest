package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrencies
import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.model.Currency
import com.platdmit.peterpartnertest.domain.repositories.CardRepo
import com.platdmit.peterpartnertest.domain.repositories.CurrencyRepo
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverterImpl
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainInteractor(
    private val cardRepo: CardRepo,
    private val currencyRepo: CurrencyRepo
) : CardUseCase {
    override fun getStartCard(): Single<Card> {
        return Single.create<Card> { emitter ->
            refreshCards()
                .andThen(refreshCurrency())
                .doOnComplete {}
                .subscribe({
                    currencyRepo.getCurrencies()
                        .flatMap { currency ->
                            val converter = getBaseCurrencyConverter(currency)
                            return@flatMap cardRepo.getCard().map {setCurrencyConverter(it, converter)}
                        }.subscribe({
                            emitter.onSuccess(it)
                        }, {
                            emitter.onError(it)
                        })
                }, {
                    emitter.onError(it)
                })
        }.subscribeOn(Schedulers.io())
    }

    override fun getCardByNumber(cardNumber: String): Single<Card> {
        return cardRepo.getCard(cardNumber)
    }

    override fun getCardForCurrency(card: Card, currencyType: CurrencyType): Single<Card> {
        return currencyRepo.getCurrency(currencyType)
            .map {
                card.currencyMod?.setConvertCurrency(it)
                return@map card
            }
    }

    override fun refreshCurrency(): Completable {
        return currencyRepo.refreshCurrency()
    }

    private fun refreshCards(): Completable {
        return cardRepo.refreshCards()
    }

    private fun getBaseCurrencyConverter(currencies: List<Currency>) : CurrencyRateConverter {
        val usdCurrency = currencies.first { it.type == CurrencyType.USD }
        val gbpCurrency = currencies.first { it.type == CurrencyType.GBP }
        return CurrencyRateConverterImpl.build(usdCurrency, gbpCurrency)
    }

    private fun setCurrencyConverter(card: Card, converter: CurrencyRateConverter) : Card{
        card.currencyMod = converter
        return card;
    }
}
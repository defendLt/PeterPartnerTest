package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.model.Currency
import com.platdmit.peterpartnertest.domain.model.Transaction
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
        return refreshCards()
            .andThen(refreshCurrency())
            .andThen(currencyRepo.getCurrencies())
            .flatMap {currency ->
                val converter = getBaseCurrencyConverter(currency)
                return@flatMap cardRepo.getCard().map { convertBalances(it, converter) }
            }.subscribeOn(Schedulers.io())
    }

    override fun getCardByNumber(cardNumber: String): Single<Card> {
        return currencyRepo.getCurrencies()
            .flatMap { currency ->
                val converter = getBaseCurrencyConverter(currency)
                return@flatMap cardRepo.getCard(cardNumber).map { convertBalances(it, converter) }
            }
    }

    override fun getCardForCurrency(card: Card, currencyType: CurrencyType): Single<Card> {
        return currencyRepo.getCurrency(currencyType)
            .map {
                card.currencyMod?.setConvertCurrency(it)
                return@map card
            }
    }

    private fun refreshCurrency(): Completable {
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

    private fun convertBalances(card: Card, converter: CurrencyRateConverter) : Card{
        convertBalanceCard(card, converter)
        card.currencyMod = converter
        card.transaction_history?.let {
            convertBalanceTransaction(it, converter)
        }
        return card
    }

    private fun convertBalanceCard(card: Card, converter: CurrencyRateConverter){
        card.modBalance = converter.getConvertValue(card.balance)
    }

    private fun convertBalanceTransaction(transactions: List<Transaction>, converter: CurrencyRateConverter){
        transactions.map {
            it.modAmount = converter.getConvertValue(it.amount)
        }
    }
}
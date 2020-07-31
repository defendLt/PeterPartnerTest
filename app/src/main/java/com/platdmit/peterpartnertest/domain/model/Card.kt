package com.platdmit.peterpartnertest.domain.model

import com.platdmit.peterpartnertest.domain.enums.CardLogoType
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter

data class Card(
    val number: String,
    val type: CardLogoType,
    val cardholder_name: String,
    val valid: String,
    val balance: Double,
    var transaction_history: List<Transaction>? = null,
    var currencyMod: CurrencyRateConverter? = null,
    var select: Boolean = false
) {
    fun getModCurrencyBalance() = currencyMod?.getConvertValue(balance) ?: balance.toBigDecimal()
    fun getModCurrencySymbol() = currencyMod?.getCurrencySymbol()
}
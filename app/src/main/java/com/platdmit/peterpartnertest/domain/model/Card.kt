package com.platdmit.peterpartnertest.domain.model

import com.platdmit.peterpartnertest.domain.enums.CardLogoType
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter
import java.math.BigDecimal

data class Card(
    val number: String,
    val type: CardLogoType,
    val cardholder_name: String,
    val valid: String,
    val balance: Double,
    var transaction_history: List<Transaction>? = null,
    var currencyMod: CurrencyRateConverter? = null,
    var modBalance: BigDecimal? = null,
    var select: Boolean = false
) {
    fun getModCurrencySymbol() = currencyMod?.getCurrencySymbol()
}
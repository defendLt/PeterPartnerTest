package com.platdmit.peterpartnertest.domain.utilities

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Currency
import java.math.BigDecimal

interface CurrencyRateConverter {
    fun getCurrencySymbol(): String
    fun getConvertValue(value: Double): BigDecimal
    fun getConvertCurrencyType(): CurrencyType
    fun setConvertCurrency(currency: Currency)
}
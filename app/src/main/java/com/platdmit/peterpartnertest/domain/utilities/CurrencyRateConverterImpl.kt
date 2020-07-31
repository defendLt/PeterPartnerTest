package com.platdmit.peterpartnertest.domain.utilities

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Currency
import java.math.BigDecimal

class CurrencyRateConverterImpl
private constructor(
    private val baseCurrency: Currency,
    private var convertCurrency: Currency
) : CurrencyRateConverter {
    override fun getCurrencySymbol(): String {
        return when(convertCurrency.type){
            CurrencyType.USD -> "₽"
            CurrencyType.EUR -> "€"
            CurrencyType.GBP -> "£"
            else -> ""
        }
    }

    override fun getConvertValue(value: Double): BigDecimal {
        val rubValue = convertToRub(value.toBigDecimal())
        return if(convertCurrency.type == CurrencyType.USD){
            rubValue.setScale(2, BigDecimal.ROUND_HALF_UP)
        } else {
            rubValue.divide(convertCurrency.getBigDecimalActualValue(), 2, BigDecimal.ROUND_HALF_UP)
        }
    }

    override fun setConvertCurrency(currency: Currency) {
        convertCurrency = currency
    }

    private fun convertToRub(value: BigDecimal): BigDecimal{
        return value*baseCurrency.getBigDecimalActualValue()
    }

    companion object{
        fun build(baseCurrency: Currency, convertCurrency: Currency) : CurrencyRateConverterImpl{
            return CurrencyRateConverterImpl(baseCurrency, convertCurrency)
        }
    }
}
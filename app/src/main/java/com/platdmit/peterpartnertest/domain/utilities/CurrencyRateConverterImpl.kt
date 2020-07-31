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
            rubValue.setScale(SCALE, ROUND)
        } else {
            rubValue.divide(convertCurrency.getBigDecimalActualValue(), SCALE, ROUND)
        }
    }

    override fun setConvertCurrency(currency: Currency) {
        convertCurrency = currency
    }

    private fun convertToRub(value: BigDecimal): BigDecimal{
        return value.multiply(baseCurrency.getBigDecimalActualValue())
    }

    companion object{
        fun build(baseCurrency: Currency, convertCurrency: Currency) : CurrencyRateConverterImpl{
            return CurrencyRateConverterImpl(baseCurrency, convertCurrency)
        }
        const val SCALE = 2
        const val ROUND = BigDecimal.ROUND_HALF_UP
    }
}
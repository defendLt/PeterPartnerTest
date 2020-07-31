package com.platdmit.peterpartnertest.domain.model

import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import java.math.BigDecimal

data class Currency(
    val charCode: String,
    val type: CurrencyType,
    val name: String,
    val actualValue: Double
){
    fun getBigDecimalActualValue(): BigDecimal{
        return actualValue.toBigDecimal()
    }
}
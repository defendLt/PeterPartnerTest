package com.platdmit.peterpartnertest.domain.model

import java.math.BigDecimal

data class Transaction(
    val name: String,
    val icon: String,
    val date: String,
    val amount: Double,
    var modAmount: BigDecimal? = null
)
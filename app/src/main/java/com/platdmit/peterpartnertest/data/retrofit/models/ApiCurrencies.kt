package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiCurrencies (
    @SerializedName("GBP")
    @Expose
    val gbp : ApiCurrency,
    @SerializedName("USD")
    @Expose
    val usd : ApiCurrency,
    @SerializedName("EUR")
    @Expose
    val eur : ApiCurrency
){
    fun getCurrencyList() : List<ApiCurrency>{
        return listOf(gbp, eur, usd)
    }
}
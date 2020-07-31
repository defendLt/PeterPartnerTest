package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiCurrenciesBody(
    @SerializedName("Date")
    @Expose
    val date: String,
    @SerializedName("Valute")
    @Expose
    val valute: ApiCurrencies
)
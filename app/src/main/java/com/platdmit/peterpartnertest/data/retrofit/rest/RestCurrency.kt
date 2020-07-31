package com.platdmit.peterpartnertest.data.retrofit.rest

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrenciesBody
import retrofit2.Call
import retrofit2.http.GET

interface RestCurrency {
    @GET("daily_json.js")
    fun getCurrencies() : Call<ApiCurrenciesBody>
}
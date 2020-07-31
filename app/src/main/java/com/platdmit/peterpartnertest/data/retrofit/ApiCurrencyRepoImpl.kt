package com.platdmit.peterpartnertest.data.retrofit

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrency
import com.platdmit.peterpartnertest.data.retrofit.rest.RestCurrency
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCurrencyRepo
import io.reactivex.rxjava3.core.Single

class ApiCurrencyRepoImpl(
    private val restCurrency: RestCurrency
) : ApiCurrencyRepo<ApiCurrency> {
    override fun getCurrency(): Single<List<ApiCurrency>> {
        return Single.create {
            try {
                val response = restCurrency.getCurrencies().execute()
                if (response.isSuccessful) {
                    it.onSuccess(response.body()!!.valute.getCurrencyList())
                } else {
                    throw Throwable(response.message())
                }
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}
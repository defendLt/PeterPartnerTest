package com.platdmit.peterpartnertest.domain.repositories.api

import io.reactivex.rxjava3.core.Single

interface ApiCurrencyRepo <ApiModel> {
    fun getCurrency() : Single<List<ApiModel>>
}
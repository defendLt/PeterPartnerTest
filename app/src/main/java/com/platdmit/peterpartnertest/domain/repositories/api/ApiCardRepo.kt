package com.platdmit.peterpartnertest.domain.repositories.api

import io.reactivex.rxjava3.core.Single

interface ApiCardRepo <ApiModel> {
    fun getCards() : Single<List<ApiModel>>
}
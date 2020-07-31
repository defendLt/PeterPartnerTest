package com.platdmit.peterpartnertest.data.retrofit

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.retrofit.rest.RestCard
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCardRepo
import io.reactivex.rxjava3.core.Single

class ApiCardRepoImpl(
    private val restCard: RestCard
) : ApiCardRepo<ApiCard> {
    override fun getCards(): Single<List<ApiCard>> {
        return Single.create {
            try {
                val response = restCard.getCards().execute()
                if (response.isSuccessful) {
                    it.onSuccess(response.body()!!.cards)
                } else {
                    throw Throwable(response.message())
                }
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}
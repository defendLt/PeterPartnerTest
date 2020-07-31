package com.platdmit.peterpartnertest.data.retrofit.rest

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCardList
import retrofit2.Call
import retrofit2.http.POST

interface RestCard {
    @POST("test/android/v1/users.json")
    fun getCards() : Call<ApiCardList>
}
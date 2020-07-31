package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiCardList(
    @SerializedName("users")
    @Expose
    val cards: List<ApiCard>
)
package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiTransaction (
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("icon_url")
    @Expose
    val icon_url: String,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("amount")
    @Expose
    val amount: String
)
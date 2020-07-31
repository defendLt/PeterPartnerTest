package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiCurrency (
    @SerializedName("ID")
    @Expose
    val id: String,
    @SerializedName("NumCode")
    @Expose
    val numCode: String,
    @SerializedName("CharCode")
    @Expose
    val charCode: String,
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("Value")
    @Expose
    val value: Double,
    @SerializedName("Previous")
    @Expose
    val previous: Double
)
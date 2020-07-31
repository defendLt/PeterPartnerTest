package com.platdmit.peterpartnertest.data.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiCard (
    @SerializedName("card_number")
    @Expose
    val card_number: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("cardholder_name")
    @Expose
    val cardholder_name: String,
    @SerializedName("valid")
    @Expose
    val valid: String,
    @SerializedName("balance")
    @Expose
    val balance: Double,
    @SerializedName("transaction_history")
    @Expose
    val transaction_history: List<ApiTransaction>?
)
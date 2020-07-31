package com.platdmit.peterpartnertest.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class DbCard (
    @PrimaryKey @NotNull val cardNumber : String,
    val type: String,
    val cardholderName: String,
    val valid: String,
    val balance: Double
)
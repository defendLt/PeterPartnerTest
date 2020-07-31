package com.platdmit.peterpartnertest.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbCurrency(
    @PrimaryKey val charCode: String,
    val name: String,
    val value: Double
)
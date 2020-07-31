package com.platdmit.peterpartnertest.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = DbCard::class, parentColumns = ["cardNumber"], childColumns = ["cardNumber"], onDelete = CASCADE)])
data class DbTransaction(
    val name: String,
    val icon: String,
    val date: String,
    val amount: Double,
    @ColumnInfo(index = true) val cardNumber: String,
    @PrimaryKey(autoGenerate = true) var id: Long?
)
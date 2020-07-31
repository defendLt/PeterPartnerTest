package com.platdmit.peterpartnertest.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<DbModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dbElement: DbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(dbElements: List<DbModel>)

    @Update
    fun update(dbElement: DbModel)

    @Update
    fun updateAll(dbElements: List<DbModel>)

    @Delete
    fun delete(dbElement: DbModel)
}
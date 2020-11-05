package com.e.expensemanager.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CreDebDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: CreDebData)

    @Delete
    suspend fun delete(expense: CreDebData)

    @Query("SELECT * FROM cre_deb WHERE type = 0")
    fun getAllCreditData(): LiveData<List<CreDebData>>

    @Query("SELECT * FROM cre_deb WHERE type = 1")
    fun getAllDebitData(): LiveData<List<CreDebData>>
}
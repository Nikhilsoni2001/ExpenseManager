package com.e.expensemanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cre_deb")
class CreDebData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val source: String,
    val amount: Int,
    val date: String,
    val type: Int
)
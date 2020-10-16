package com.e.expensemanager.ui.mvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    val source: String,
    val amount: Int,
    val date: String,
    val type: Int
    //0 means increase; 1 means decrease;
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int ?= null
}
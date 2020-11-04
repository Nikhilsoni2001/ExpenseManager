package com.e.expensemanager.ui

import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.db.ExpenseDatabase
import com.e.expensemanager.db.Expense

class ExpenseRepository(
    private val db: ExpenseDatabase
) {
    suspend fun upsert(expense: Expense) = db.getExpenseDao().upsert(expense)
    suspend fun delete(expense: Expense) = db.getExpenseDao().delete(expense)
    fun getAllExpenses() = db.getExpenseDao().getAllExpenses()
    suspend fun insert(expense: CreDebData) = db.getCredDebDao().insert(expense)
    suspend fun deleteCD(expense: CreDebData) = db.getCredDebDao().delete(expense)
    fun getAllData() = db.getCredDebDao().getAllData()

}
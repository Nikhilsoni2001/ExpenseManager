package com.e.expensemanager.ui.mvvm

class ExpenseRepository(
    private val db: ExpenseDatabase
) {
    suspend fun upsert(expense: Expense) = db.getExpenseDao().upsert(expense)
    suspend fun delete(expense: Expense) = db.getExpenseDao().delete(expense)
    fun getAllExpenses() = db.getExpenseDao().getAllExpenses()
}
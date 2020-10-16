package com.e.expensemanager.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository
): ViewModel() {
    fun upsert(expense: Expense) = viewModelScope.launch {
        repository.upsert(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }

    fun getAllExpenses() = repository.getAllExpenses()

}
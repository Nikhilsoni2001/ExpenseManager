package com.e.expensemanager.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.expensemanager.ui.adapters.TokenSharedPreferenceLiveData
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository, private val tokenSharedPreferenceLiveData: TokenSharedPreferenceLiveData): ViewModel() {
    fun upsert(expense: Expense) = viewModelScope.launch {
        repository.upsert(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }

    fun getAllExpenses() = repository.getAllExpenses()

    fun getTokenLiveData() = tokenSharedPreferenceLiveData

}
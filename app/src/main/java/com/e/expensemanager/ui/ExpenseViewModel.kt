package com.e.expensemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.util.TokenSharedPreferenceLiveData
import com.e.expensemanager.db.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository,
    private val tokenSharedPreferenceLiveData: TokenSharedPreferenceLiveData
) : ViewModel() {
    fun upsert(expense: Expense) = viewModelScope.launch {
        repository.upsert(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }

    fun getAllExpenses() = repository.getAllExpenses()

    fun getTokenLiveData() = tokenSharedPreferenceLiveData

    fun insert(expense: CreDebData) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun deleteCD(credDeb: CreDebData) = viewModelScope.launch {
        repository.deleteCD(credDeb)
    }

    fun getAllCreditData() = repository.getAllCreditData()
    fun getAllDebitData() = repository.getAllDebitData()
}
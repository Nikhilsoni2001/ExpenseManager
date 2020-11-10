package com.e.expensemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.db.Expense
import com.e.expensemanager.util.Preferences
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository,
    private val preferences: Preferences
) : ViewModel() {
    fun upsert(expense: Expense) = viewModelScope.launch {
        repository.upsert(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }

    fun getAllExpenses() = repository.getAllExpenses()

    fun getCurrency() = preferences.getCurrency()
    fun setCurrency(currency: String) = preferences.setCurrency(currency)

    fun insert(expense: CreDebData) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun deleteCD(credDeb: CreDebData) = viewModelScope.launch {
        repository.deleteCD(credDeb)
    }

    fun getAllCreditData() = repository.getAllCreditData()
    fun getAllDebitData() = repository.getAllDebitData()
}
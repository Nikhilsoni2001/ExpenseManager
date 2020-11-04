package com.e.expensemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.expensemanager.ui.adapters.TokenSharedPreferenceLiveData

class ExpenseViewModelProviderFactory(
    private val repository: ExpenseRepository,
    private val tokenSharedPreferenceLiveData: TokenSharedPreferenceLiveData
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(repository,tokenSharedPreferenceLiveData) as T
    }
}
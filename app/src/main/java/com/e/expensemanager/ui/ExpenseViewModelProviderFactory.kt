package com.e.expensemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.expensemanager.util.Preferences

class ExpenseViewModelProviderFactory(
    private val repository: ExpenseRepository,
    private val preferences: Preferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(repository, preferences) as T
    }
}
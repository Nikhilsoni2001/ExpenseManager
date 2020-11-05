package com.e.expensemanager.util

import android.content.Context

class Preferences(context: Context) {

    companion object {
        private val PRIVATE_MODE = 0
        private val PREF_NAME = "myExpense"
        private val KEY_CURRENCY = "currency"
    }

    private val pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor = pref.edit()

    fun setCurrency(currency: String) {
        editor.putString(KEY_CURRENCY, currency)
        editor.apply()
    }

    fun getCurrency(): String? {
        return pref.getString(KEY_CURRENCY, null)
    }
}
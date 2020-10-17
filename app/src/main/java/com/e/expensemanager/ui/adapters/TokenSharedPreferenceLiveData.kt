package com.e.expensemanager.ui.adapters

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class TokenSharedPreferenceLiveData(private val sharedPreferences: SharedPreferences) : LiveData<Int>() {

    private val mTokenSharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener({ sharedPreferences: SharedPreferences?, key: String? ->
            if (key == MYKEYSTRING) {
                value = sharedPreferences?.getInt(MYKEYSTRING, 27)
            }
        })


    override fun onActive() {
        super.onActive()
        value = sharedPreferences.getInt(MYKEYSTRING, 27)
        sharedPreferences.registerOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    companion object {
        private const val MYKEYSTRING = "amount"

    }
}
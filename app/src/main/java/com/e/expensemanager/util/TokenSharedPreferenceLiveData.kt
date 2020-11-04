package com.e.expensemanager.util

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class TokenSharedPreferenceLiveData(private val sharedPreferences: SharedPreferences) :
    LiveData<Int>() {

    private val mTokenSharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key: String? ->
            if (key == MY_KEY_STRING) {
                value = sharedPreferences?.getInt(MY_KEY_STRING, 27)
            }
        }

    override fun onActive() {
        super.onActive()
        value = sharedPreferences.getInt(MY_KEY_STRING, 27)
        sharedPreferences.registerOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    companion object {
        private const val MY_KEY_STRING = "amount"

    }
}
package com.e.expensemanager.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.expensemanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val sharedPref = getSharedPreferences("myExpense", Context.MODE_PRIVATE)
        val currency = sharedPref.getString("currency",null)


        CoroutineScope(Dispatchers.Main).launch {

            if(currency==null) {
                val onBoardingActivity = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                delay(3000)
                startActivity(onBoardingActivity)
                finish()
            } else {
                val expenseActivity = Intent(this@SplashActivity, ExpenseActivity::class.java)
                delay(3000)
                startActivity(expenseActivity)
                finish()
            }
        }



    }
}
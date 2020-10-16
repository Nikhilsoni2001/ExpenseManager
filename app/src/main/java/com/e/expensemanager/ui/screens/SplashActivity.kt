package com.e.expensemanager.ui.screens

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


        CoroutineScope(Dispatchers.Main).launch {
            val onBoardingActivity = Intent(this@SplashActivity,OnBoardingActivity::class.java)
            delay(3000)
            startActivity(onBoardingActivity)
            finish()
        }



    }
}
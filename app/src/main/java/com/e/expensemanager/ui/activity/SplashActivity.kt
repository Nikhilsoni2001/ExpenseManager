package com.e.expensemanager.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.e.expensemanager.R
import com.e.expensemanager.db.ExpenseDatabase
import com.e.expensemanager.ui.ExpenseRepository
import com.e.expensemanager.ui.ExpenseViewModel
import com.e.expensemanager.ui.ExpenseViewModelProviderFactory
import com.e.expensemanager.util.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var viewModel: ExpenseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val database = ExpenseDatabase(this)
        val repository = ExpenseRepository(database)
        val factory = ExpenseViewModelProviderFactory(repository, Preferences(this@SplashActivity))
        viewModel = ViewModelProvider(this, factory).get(ExpenseViewModel::class.java)

        val currency = viewModel.getCurrency()

        CoroutineScope(Dispatchers.Main).launch {
            if (currency == null) {
                delay(3000)
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                finish()
            } else {
                delay(3000)
                startActivity(Intent(this@SplashActivity, ExpenseActivity::class.java))
                finish()
            }
        }
    }
}
package com.e.expensemanager.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.expensemanager.R
import com.e.expensemanager.db.ExpenseDatabase
import com.e.expensemanager.ui.ExpenseRepository
import com.e.expensemanager.ui.ExpenseViewModel
import com.e.expensemanager.ui.ExpenseViewModelProviderFactory
import com.e.expensemanager.util.Preferences
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    lateinit var spCurrency: Spinner
    lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val database = ExpenseDatabase(this)
        val repository = ExpenseRepository(database)
        val factory =
            ExpenseViewModelProviderFactory(repository, Preferences(this@OnBoardingActivity))
        viewModel = ViewModelProvider(this, factory).get(ExpenseViewModel::class.java)

        hooks()
        var currency = "empty"

        spCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> currency = "₹"
                    1 -> currency = "$"
                    2 -> currency = "€"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        btnGetStarted.setOnClickListener {
            if (currency != "empty") {
                viewModel.setCurrency(currency)
                startActivity(Intent(this@OnBoardingActivity, ExpenseActivity::class.java))
            } else {
                Toast.makeText(this, "Please select a currency unit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hooks() {
        spCurrency = findViewById(R.id.spCurrency)
    }
}
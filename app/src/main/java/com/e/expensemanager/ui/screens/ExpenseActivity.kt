package com.e.expensemanager.ui.screens

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.e.expensemanager.R
import com.e.expensemanager.ui.adapters.TokenSharedPreferenceLiveData
import com.e.expensemanager.ui.mvvm.ExpenseDatabase
import com.e.expensemanager.ui.mvvm.ExpenseRepository
import com.e.expensemanager.ui.mvvm.ExpenseViewModel
import com.e.expensemanager.ui.mvvm.ExpenseViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_expense.*

class ExpenseActivity : AppCompatActivity() {



    lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        val sharedPref = getSharedPreferences("myExpense", Context.MODE_PRIVATE)

        val database = ExpenseDatabase(this)
        val repository = ExpenseRepository(database)
        val factory = ExpenseViewModelProviderFactory(repository, TokenSharedPreferenceLiveData(sharedPref))
        viewModel = ViewModelProvider(this,factory).get(ExpenseViewModel::class.java)


        val navHeaderView = navView.inflateHeaderView(R.layout.nav_header)
        navHeaderView.setOnClickListener {


            findNavController(R.id.fragment).navigate(R.id.expenseGraphFragment)
        }


        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.fragment)
        navView.setupWithNavController(navController)




        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miExpenses -> navController.navigate(R.id.expenseFragment)
                R.id.miIncome -> navController.navigate(R.id.incomeFragment)
                R.id.miContact -> Toast.makeText(this,"Contact",Toast.LENGTH_LONG).show()
                R.id.miExit -> Toast.makeText(this,"Exit",Toast.LENGTH_LONG).show()

            }
            true
        }



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
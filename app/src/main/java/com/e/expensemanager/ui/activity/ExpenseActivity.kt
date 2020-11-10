package com.e.expensemanager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.e.expensemanager.R
import com.e.expensemanager.db.ExpenseDatabase
import com.e.expensemanager.ui.ExpenseRepository
import com.e.expensemanager.ui.ExpenseViewModel
import com.e.expensemanager.ui.ExpenseViewModelProviderFactory
import com.e.expensemanager.util.Preferences
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.view.*

class ExpenseActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewModel: ExpenseViewModel
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var previousItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)

        val database = ExpenseDatabase(this)
        val repository = ExpenseRepository(database)
        val factory = ExpenseViewModelProviderFactory(repository, Preferences(this@ExpenseActivity))
        viewModel = ViewModelProvider(this, factory).get(ExpenseViewModel::class.java)

        val navHeaderView = navView.inflateHeaderView(R.layout.nav_header)

        navHeaderView.btnProfit.setOnClickListener {
            findNavController(R.id.fragment).navigate(R.id.expenseGraphFragment)
        }
        navHeaderView.btnLoss.setOnClickListener {
            findNavController(R.id.fragment).navigate(R.id.expenseLossGraphFragment)
        }
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.fragment)
        navView.setupWithNavController(navController)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            if (previousItem != null) {
                previousItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousItem = it

            when (it.itemId) {
                R.id.miExpenses -> {
                    navController.navigate(R.id.expenseFragment)
                    navView.setCheckedItem(R.id.expenseFragment)
                    drawerLayout.closeDrawer(navView)
                }
                R.id.miDashboard -> {
                    navController.navigate(R.id.dashboardFragment)
                    navView.setCheckedItem(R.id.miDashboard)
                    drawerLayout.closeDrawer(navView)
                }
                R.id.miContact -> Toast.makeText(this, "Contact", Toast.LENGTH_LONG).show()
                R.id.miExit -> Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
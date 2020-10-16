package com.e.expensemanager.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.e.expensemanager.R
import kotlinx.android.synthetic.main.activity_expense.*

class ExpenseActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()




        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miExpenses -> Toast.makeText(this,"Expenses",Toast.LENGTH_LONG).show()
                R.id.miIncome -> Toast.makeText(this,"Income",Toast.LENGTH_LONG).show()
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
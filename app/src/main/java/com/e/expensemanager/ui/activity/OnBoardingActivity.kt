package com.e.expensemanager.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.e.expensemanager.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)



        var currency = "empty"
        val sharedPref = getSharedPreferences("myExpense",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        spCurrency.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2==0)
                    currency = "₹"
                else if(p2==1)
                    currency = "$"
                else if(p2==2)
                    currency = "€"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        btnIshant.setOnClickListener {
            if(etAmount1.text.isNotEmpty()) {
                if(currency!="empty") {
                    //Toast.makeText(this, "$currency", Toast.LENGTH_SHORT).show()
                    editor.apply {
                        putInt("amount",etAmount1.text.toString().toInt())
                        putString("currency",currency)
                        apply()
                    }
                    Toast.makeText(this, "Success ${etAmount1.text.toString().toInt()} $currency", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "Please select a currency unit", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select amount", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
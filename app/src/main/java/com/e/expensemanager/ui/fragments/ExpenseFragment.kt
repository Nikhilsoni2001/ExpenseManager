package com.e.expensemanager.ui.fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.e.expensemanager.R
import kotlinx.android.synthetic.main.fragment_expense.*


class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("myExpense", Context.MODE_PRIVATE)
        val amount = sharedPref?.getInt("amount",0)
        val currency = sharedPref?.getString("currency",null)

        tvExpense.text = "$currency $amount"

        btnIncrease.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.expense_change_dialog_box,null)
            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView).setTitle("Please fill the details").create()
            val dialogBox = dialogBuilder.show()
        }


    }


}
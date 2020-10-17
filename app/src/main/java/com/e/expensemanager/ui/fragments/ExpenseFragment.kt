package com.e.expensemanager.ui.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.expensemanager.R
import com.e.expensemanager.ui.adapters.ExpenseAdapter
import com.e.expensemanager.ui.mvvm.Expense
import com.e.expensemanager.ui.screens.ExpenseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.expense_change_dialog_box.view.*
import kotlinx.android.synthetic.main.fragment_expense.*
import java.text.SimpleDateFormat
import java.util.*


class ExpenseFragment : Fragment(R.layout.fragment_expense) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as ExpenseActivity).viewModel

        val sharedPref = activity?.getSharedPreferences("myExpense", Context.MODE_PRIVATE)
        var amount = sharedPref?.getInt("amount", 0)
        val currency = sharedPref?.getString("currency", null)
        val editor = sharedPref?.edit()
        viewModel.getTokenLiveData().observe(viewLifecycleOwner, Observer {
            tvExpense.text = " ${sharedPref?.getString("currency", null)} $it"

        })





        btnIncrease.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext()).inflate(
                R.layout.expense_change_dialog_box,
                null)
            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView).setTitle("Please fill the details")
            val dialogBox = dialogBuilder.show()
            val etSourceVal = dialogView.findViewById<EditText>(R.id.etSource)
            val etAmountVal = dialogView.findViewById<EditText>(R.id.etAmount)
            val sourceVal = etSourceVal.text
            val amountVal = etAmountVal.text
            dialogView.btnSave.setOnClickListener {
                Toast.makeText(requireContext(), "$currency$amountVal from $sourceVal added", Toast.LENGTH_LONG).show()
                if(sourceVal.isNotEmpty()) {
                    if(amountVal.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMMM yyyy, hh:mm")
                        val currentDate = sdf.format(Date())
                        val expense = Expense(
                            0,
                            sourceVal.toString(),
                            amountVal.toString().toInt(),
                            "$currentDate",
                            0
                        )
                        amount = amount?.plus(amountVal.toString().toInt())
                        editor?.putInt("amount", amount!!)?.apply()
                        viewModel.upsert(expense)
                    } else { Toast.makeText(requireContext(), "Please enter Amount", Toast.LENGTH_LONG).show() }
                } else { Toast.makeText(requireContext(), "Please enter Source", Toast.LENGTH_LONG).show() }
            }
        }


        btnDecrease.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext()).inflate(
                R.layout.expense_change_dialog_box,
                null)
            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView).setTitle("Please fill the details")
            val dialogBox = dialogBuilder.show()
            val etSourceVal = dialogView.findViewById<EditText>(R.id.etSource)
            val etAmountVal = dialogView.findViewById<EditText>(R.id.etAmount)
            val sourceVal = etSourceVal.text
            val amountVal = etAmountVal.text
            dialogView.btnSave.setOnClickListener {
                Toast.makeText(requireContext(), "$currency$amountVal from $sourceVal reduced", Toast.LENGTH_LONG).show()
                if(sourceVal.isNotEmpty()) {
                    if(amountVal.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMMM yyyy, hh:mm")
                        val currentDate = sdf.format(Date())
                        val expense = Expense(
                            0,
                            sourceVal.toString(),
                            amountVal.toString().toInt(),
                            "$currentDate",
                            1
                        )
                        amount = amount?.minus(amountVal.toString().toInt())
                        editor?.putInt("amount", amount!!)?.apply()
                        viewModel.upsert(expense)
                    } else { Toast.makeText(requireContext(), "Please enter Amount", Toast.LENGTH_LONG).show() }
                } else { Toast.makeText(requireContext(), "Please enter Source", Toast.LENGTH_LONG).show() }
            }
        }


























        val expenseAdapter = ExpenseAdapter(requireContext())
        rvExpenses.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val expense = expenseAdapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.delete(expense)

                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.upsert(expense)
                        /*
                        if(expense.type==0) {
                            val newAmount = (amount?.plus(expense.amount)?.div(2))
                            editor?.putInt("amount", newAmount!!)?.apply()
                        } else {
                            val newAmount = (amount?.minus(expense.amount)?.div(2))
                            editor?.putInt("amount", newAmount!!)?.apply()
                        }

                       */
                    }
                    show() }




                /*
                AlertDialog.Builder(requireContext()).setTitle("Expense Manager").setMessage("Also make changes to Amount?")
                    .setPositiveButton("Ok") {dialogInterface, i ->

                        if(expense.type==0) {
                            val newAmount = amount?.minus(expense.amount)
                            editor?.putInt("amount", newAmount!!)?.apply()
                        } else {
                            val newAmount = amount?.plus(expense.amount)
                            editor?.putInt("amount", newAmount!!)?.apply()
                        }

                        }
                        dialogInterface.dismiss()
                    }.setNegativeButton("Cancle") {dialogInterface, i ->
                        Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") {
                                viewModel.upsert(expense)
                            }
                            show()
                        }
                        dialogInterface.dismiss()
                    }.create().show()

            */




            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvExpenses)





        viewModel.getAllExpenses().observe(viewLifecycleOwner, Observer { expenseList ->
            expenseAdapter.differ.submitList(expenseList)
        })


    }


}
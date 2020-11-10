package com.e.expensemanager.ui.fragments


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.expensemanager.R
import com.e.expensemanager.ui.adapters.ExpenseAdapter
import com.e.expensemanager.db.Expense
import com.e.expensemanager.ui.activity.ExpenseActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_expense.*
import java.text.SimpleDateFormat
import java.util.*

class ExpenseFragment : Fragment(R.layout.fragment_expense) {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as ExpenseActivity).viewModel

        val currency = viewModel.getCurrency()

        tvExpense.text = "$currency 0"

        btnIncrease.setOnClickListener {
            val dialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.custom_dialog_box, null)

            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
                .setTitle("Please Fill the details")
            val dialogBox = dialogBuilder.show()

            val elAmount = dialogView.findViewById<TextInputLayout>(R.id.elAmount)
            val etAmount = dialogView.findViewById<TextInputEditText>(R.id.etAmount)
            val elSource = dialogView.findViewById<TextInputLayout>(R.id.elSource)
            val etSource = dialogView.findViewById<TextInputEditText>(R.id.etSource)
            val btnSave = dialogView.findViewById<MaterialButton>(R.id.btnSave)

            btnSave.setOnClickListener {
                if (etAmount.text?.trim()?.isNotEmpty()!!) {
                    elAmount.error = null
                    if (etSource.text?.trim()?.isNotEmpty()!!) {
                        elSource.error = null
                        // update
                        val sdf = SimpleDateFormat("dd MMMM yyyy, hh:mm")
                        val currentDate = sdf.format(Date())
                        val expense = Expense(
                            0,
                            etSource.text.toString(),
                            etAmount.text.toString().toInt(),
                            currentDate,
                            0
                        )
                        viewModel.upsert(expense)
                        dialogBox.cancel()
                    } else {
                        elSource.error = "Please enter a valid Source Name"
                    }
                } else {
                    elAmount.error = "Please Enter a valid amount"
                }
            }
        }

        btnDecrease.setOnClickListener {
            val dialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.custom_dialog_box, null)

            val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
                .setTitle("Please Fill the details")
            val dialogBox = dialogBuilder.show()

            val elAmount = dialogView.findViewById<TextInputLayout>(R.id.elAmount)
            val etAmount = dialogView.findViewById<TextInputEditText>(R.id.etAmount)
            val elSource = dialogView.findViewById<TextInputLayout>(R.id.elSource)
            val etSource = dialogView.findViewById<TextInputEditText>(R.id.etSource)
            val btnSave = dialogView.findViewById<MaterialButton>(R.id.btnSave)

            btnSave.setOnClickListener {
                if (etAmount.text?.trim()?.isNotEmpty()!!) {
                    elAmount.error = null
                    if (etSource.text?.trim()?.isNotEmpty()!!) {
                        elSource.error = null
                        // update
                        val sdf = SimpleDateFormat("dd MMMM yyyy, hh:mm")
                        val currentDate = sdf.format(Date())

                        val expense = Expense(
                            0,
                            etSource.text.toString(),
                            etAmount.text.toString().toInt(),
                            currentDate,
                            1
                        )
                        viewModel.upsert(expense)
                        dialogBox.cancel()
                    } else {
                        elSource.error = "Please enter a valid Source Name"
                    }
                } else {
                    elAmount.error = "Please Enter a valid amount"
                }
            }
        }

        val expenseAdapter = ExpenseAdapter()
        rvExpenses.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
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
                    }
                    show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvExpenses)

        viewModel.getAllExpenses().observe(viewLifecycleOwner, { expenseList ->
            expenseAdapter.differ.submitList(expenseList)
        })
    }
}
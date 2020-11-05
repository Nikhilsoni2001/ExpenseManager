package com.e.expensemanager.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.expensemanager.R
import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.ui.activity.ExpenseActivity
import com.e.expensemanager.ui.adapters.CreDebAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


class DebitFragment : Fragment() {
    private lateinit var fabDebit: FloatingActionButton
    private lateinit var rvDebit: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_debit, container, false)
        hooks(view)
        val viewModel = (activity as ExpenseActivity).viewModel
        val credDebAdapter = CreDebAdapter()
        rvDebit.apply {
            rvDebit.adapter = credDebAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getAllDebitData().observe(viewLifecycleOwner, Observer { debList ->
            credDebAdapter.differ.submitList(debList)
        })

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
                val credDeb = credDebAdapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.deleteCD(credDeb)

                Snackbar.make(view, "Deleted Successfully!!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insert(credDeb)
                    }
                    show()
                }
            }
        }


        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvDebit)

        fabDebit.setOnClickListener {
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
                        val data = CreDebData(
                            0,
                            etSource.text.toString().trim(),
                            etAmount.text.toString().toInt(),
                            currentDate,
                            1
                        )
                        viewModel.insert(data)
                        dialogBox.cancel()
                    } else {
                        elSource.error = "Please enter a valid Source Name"
                    }
                } else {
                    elAmount.error = "Please Enter a valid amount"
                }
            }
        }

        return view
    }

    private fun hooks(view: View) {
        fabDebit = view.findViewById(R.id.fabDebit)
        rvDebit = view.findViewById(R.id.rvDebit)
    }
}
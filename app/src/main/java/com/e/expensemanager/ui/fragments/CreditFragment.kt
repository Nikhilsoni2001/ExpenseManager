package com.e.expensemanager.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e.expensemanager.R
import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.ui.ExpenseActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class CreditFragment : Fragment(R.layout.fragment_credit) {

    lateinit var fabCredit: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_credit, container, false)
        hooks(view)

        val viewModel = (activity as ExpenseActivity).viewModel

        fabCredit.setOnClickListener {
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
                        val data = CreDebData(0, etSource.text.toString().trim(), etAmount.text.toString().toInt(), currentDate, 0)
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
        fabCredit = view.findViewById(R.id.fabCredit)
    }
}
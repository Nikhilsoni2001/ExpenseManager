package com.e.expensemanager.ui.fragments


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.e.expensemanager.R
import com.e.expensemanager.ui.ExpenseViewModel
import com.e.expensemanager.ui.ExpenseActivity
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_profit_expense_graph.*

class ExpenseProfitGraphFragment : Fragment(R.layout.fragment_profit_expense_graph) {

    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet

    lateinit var viewModel: ExpenseViewModel




    var barEntries = mutableListOf<BarEntry>()



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ExpenseActivity).viewModel

        if(barEntries.isEmpty()) {
            Toast.makeText(requireContext(),"Loading, Please tap on screen to proceed",Toast.LENGTH_LONG).show()
        }


        viewModel.getAllExpenses().observe(viewLifecycleOwner, Observer { expenses ->
            for (expense in expenses) {
                if(expense.type==0) {

                    barEntries.add(
                        BarEntry(
                            expenses.indexOf(expense).toFloat(),
                            expense.amount.toFloat()
                        )
                    )
                }
            }




            barDataSet = BarDataSet(barEntries, "Data Set")
            barData = BarData(barDataSet)

            barChart.data = barData
            var colorList = mutableListOf<Int>()

            for (i in 0..ColorTemplate.MATERIAL_COLORS.size - 1) {
                colorList.add(ColorTemplate.MATERIAL_COLORS[i])
            }


            barDataSet.colors = colorList

            barDataSet.valueTextColor = requireContext().getColor(R.color.black)
            barDataSet.valueTextSize = 16f

        })
    }



}








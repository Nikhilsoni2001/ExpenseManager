package com.e.expensemanager.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.expensemanager.R
import com.e.expensemanager.db.Expense
import kotlinx.android.synthetic.main.expense.view.*

class ExpenseAdapter(context: Context): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val sharedPref = context.getSharedPreferences("myExpense", Context.MODE_PRIVATE)
    private val currency = sharedPref?.getString("currency",null)

    private val differCallback = object: DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense,parent,false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = differ.currentList[position]
        holder.itemView.apply {
            tvSourceText.text = expense.source
            tvDate.text = expense.date
            if(expense.type==0) {
                tvAmount.setTextColor(ContextCompat.getColor(context,R.color.green))
                tvAmount.text = "+ $currency${expense.amount}"
            } else {
                tvAmount.setTextColor(ContextCompat.getColor(context,R.color.red))
                tvAmount.text = "- $currency${expense.amount}"
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
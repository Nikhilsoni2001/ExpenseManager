package com.e.expensemanager.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.expensemanager.R
import com.e.expensemanager.db.CreDebData
import com.e.expensemanager.util.Preferences
import kotlinx.android.synthetic.main.expense.view.*

class CreDebAdapter : RecyclerView.Adapter<CreDebAdapter.CreDebViewHolder>() {
    private lateinit var currency: String

    inner class CreDebViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val differCallback = object : DiffUtil.ItemCallback<CreDebData>() {
        override fun areItemsTheSame(oldItem: CreDebData, newItem: CreDebData): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CreDebData, newItem: CreDebData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreDebViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense, parent, false)
        currency = Preferences(parent.context).getCurrency().toString()
        return CreDebViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CreDebViewHolder, position: Int) {
        val credDeb = differ.currentList[position]
        holder.itemView.apply {
            tvSourceText.text = credDeb.source
            tvDate.text = credDeb.date
            if (credDeb.type == 0) {
                tvAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                tvAmount.text = "+ $currency${credDeb.amount}"
            } else {
                tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                tvAmount.text = "- $currency${credDeb.amount}"
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}
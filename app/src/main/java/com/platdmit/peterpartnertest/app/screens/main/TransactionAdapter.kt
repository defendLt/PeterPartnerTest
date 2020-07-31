package com.platdmit.peterpartnertest.app.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.domain.model.Transaction
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter
import com.squareup.picasso.Picasso

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private val transactionList : MutableList<Transaction> = mutableListOf()
    private lateinit var currencyRateConverter: CurrencyRateConverter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutType = R.layout.fragment_transaction
        return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(layoutType, parent, false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindData(transactionList[position])
    }

    fun setData(transactions : List<Transaction>, currencyRateConverter: CurrencyRateConverter){
        transactionList.clear()
        transactionList.addAll(transactions)
        this.currencyRateConverter = currencyRateConverter
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val logo = itemView.findViewById<ImageView>(R.id.transaction_logo)
        private val title = itemView.findViewById<TextView>(R.id.transaction_title)
        private val date = itemView.findViewById<TextView>(R.id.transaction_date)
        private val valueBase = itemView.findViewById<TextView>(R.id.currency_base)
        private val valueMod = itemView.findViewById<TextView>(R.id.currency_select)

        fun bindData(transaction: Transaction){
            transaction.icon.let {
                Picasso.get()
                    .load(it)
                    .into(logo)
            }
            title.text = transaction.name
            date.text = transaction.date
            valueBase.text = transaction.amount.toString().replace("-", "${itemView.context.getText(R.string.currency_usd)} ")
            valueMod.text = "- ${currencyRateConverter.getCurrencySymbol()} ${currencyRateConverter.getConvertValue(transaction.amount).toString().replace("-", "")}"
        }
    }
}
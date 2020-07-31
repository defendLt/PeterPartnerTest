package com.platdmit.peterpartnertest.app.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.app.utilities.ShowLoaderHandler
import com.platdmit.peterpartnertest.app.utilities.ShowMessageHandler
import com.platdmit.peterpartnertest.app.utilities.enums.CardBundleType
import com.platdmit.peterpartnertest.app.utilities.enums.MessageType
import com.platdmit.peterpartnertest.domain.enums.CardLogoType
import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.model.Transaction
import com.platdmit.peterpartnertest.domain.utilities.CurrencyRateConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.DecimalFormat

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ShowMessageHandler {
    private val transactionAdapter = TransactionAdapter()
    private val mainViewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transaction_recycler_view.layoutManager = LinearLayoutManager(context)

        mainViewModel.cardLiveData.observe(viewLifecycleOwner, Observer { parsObservableData(it) })

        mainViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { bindMessageData(it) })

        initClickListeners()
    }

    private fun bindAdapterData(transactions : List<Transaction>, currencyRateConverter: CurrencyRateConverter){
        transactionAdapter.setData(transactions, currencyRateConverter)
        if(transaction_recycler_view.adapter == null){
            transaction_recycler_view.adapter = transactionAdapter
        }
    }

    private fun bindMessageData(messageType: MessageType){
        when(messageType){
            MessageType.EMPTY -> showMessage(getString(R.string.message_empty))
            MessageType.FALL -> showMessage(getString(R.string.message_fall))
            MessageType.FALL_CONNECT -> showMessage(getString(R.string.message_fall_connect))
        }
    }

    private fun bindCurrencyData(currencyType: CurrencyType){
        resetCurrencySelect()
        when(currencyType){
            CurrencyType.GBP -> {
                change_gbp.isSelected = true
                change_gbp_code.isSelected = true
                change_gbp_symbol.isSelected = true
            }
            CurrencyType.EUR -> {
                change_eur.isSelected = true
                change_eur_code.isSelected = true
                change_eur_symbol.isSelected = true
            }
            else -> {
                change_rub.isSelected = true
                change_rub_code.isSelected = true
                change_rub_symbol.isSelected = true
            }
        }
    }

    private fun parsObservableData(card: Card){
        val valueFormat = DecimalFormat("#,###.##")
        card_number.text = card.number
        card_name.text = card.cardholder_name
        card_logo.setImageResource(getCardLogo(card.type))
        card_valid.text = card.valid
        card_balance_dop.text = "${card.getModCurrencySymbol()}${valueFormat.format(card.getModCurrencyBalance())}"
        card_balance_value.text =  "${context?.getText(R.string.currency_usd)}${valueFormat.format(card.balance)}"

        card.transaction_history?.let {
            bindAdapterData(it, card.currencyMod!!)
        }

        bindCurrencyData(card.currencyMod?.getConvertCurrencyType()?:CurrencyType.GBP)

        (activity as? ShowLoaderHandler)?.loaderVisible(false)
    }

    @SuppressLint("ResourceAsColor")
    private fun initClickListeners() {
        card_detail.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_cardsFragment, bundleOf(
                CardBundleType.ACTIVE_CARD.name to card_number.text
            ))
        }

        change_gbp.setOnClickListener {
            mainViewModel.changeModCurrency(CurrencyType.GBP)
        }

        change_eur.setOnClickListener {
            mainViewModel.changeModCurrency(CurrencyType.EUR)
        }

        change_rub.setOnClickListener {
            mainViewModel.changeModCurrency(CurrencyType.USD)
        }
    }

    private fun getCardLogo(cardLogoType: CardLogoType) : Int{
        return when(cardLogoType){
            CardLogoType.MASTERCARD -> R.drawable.ic_mastercard
            CardLogoType.VISA -> R.drawable.ic_visa
            CardLogoType.UNIONPAY -> R.drawable.ic_union
            CardLogoType.UNKNOWN -> R.drawable.background_box_w
        }
    }

    private fun resetCurrencySelect(){
        change_rub.isSelected = false
        change_gbp.isSelected = false
        change_eur.isSelected = false
        change_gbp_code.isSelected = false
        change_eur_code.isSelected = false
        change_rub_code.isSelected = false
        change_gbp_symbol.isSelected = false
        change_eur_symbol.isSelected = false
        change_rub_symbol.isSelected = false
    }

    override fun showMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}
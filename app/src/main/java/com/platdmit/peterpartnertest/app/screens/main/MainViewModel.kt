package com.platdmit.peterpartnertest.app.screens.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.platdmit.peterpartnertest.app.base.BaseViewModel
import com.platdmit.peterpartnertest.app.utilities.enums.CardBundleType
import com.platdmit.peterpartnertest.app.utilities.enums.MessageType
import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.usecase.CardUseCase

class MainViewModel
@ViewModelInject
constructor(
    private val cardUseCase: CardUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(){
    val cardLiveData : MutableLiveData<Card> = savedStateHandle.getLiveData("card")

    init {
        savedStateHandle.get<String>(CardBundleType.SET_ACTIVE_CARD.name)?.let {
            getActiveCard(it)
        } ?: getStartCard()
    }

    private fun getStartCard(){
        compositeDisposable.add(
            cardUseCase.getStartCard().onErrorComplete{
                messageLiveData.postValue(MessageType.FALL)
                true
            }.subscribe({
                cardLiveData.postValue(it)
            }, {
                messageLiveData.postValue(MessageType.FALL)
            })
        )
    }

    private fun getActiveCard(cardNumber : String) {
        compositeDisposable.add(
            cardUseCase.getCardByNumber(cardNumber)
                .onErrorComplete{
                    messageLiveData.postValue(MessageType.FALL)
                    true
                }.subscribe({
                cardLiveData.postValue(it)
            }, {
                messageLiveData.postValue(MessageType.FALL)
            })
        )
    }

    fun changeModCurrency(currencyType: CurrencyType){
        compositeDisposable.add(
            cardUseCase.getCardForCurrency(cardLiveData.value!!, currencyType)
                .onErrorComplete{
                    messageLiveData.postValue(MessageType.FALL)
                    true
                }.subscribe({
                cardLiveData.postValue(it)
            }, {
                messageLiveData.postValue(MessageType.FALL)
            })
        )
    }
}
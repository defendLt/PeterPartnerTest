package com.platdmit.peterpartnertest.app.screens.cards

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.platdmit.peterpartnertest.app.base.BaseViewModel
import com.platdmit.peterpartnertest.app.utilities.enums.CardBundleType
import com.platdmit.peterpartnertest.app.utilities.enums.MessageType
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.usecase.CardsUseCase

class CardsViewModel @ViewModelInject
constructor(
    private val cardsUseCase: CardsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val cardsLiveData: MutableLiveData<List<Card>> = MutableLiveData()

    init {
        savedStateHandle.get<String>(CardBundleType.ACTIVE_CARD.name)?.let { card_number ->
            compositeDisposable.add(
                cardsUseCase.getCards(card_number).onErrorComplete {
                    messageLiveData.postValue(MessageType.FALL)
                    true
                }.subscribe(
                    {
                        cardsLiveData.postValue(it)
                    }, {
                        messageLiveData.postValue(MessageType.FALL)
                    }
                )
            )
        }
    }
}
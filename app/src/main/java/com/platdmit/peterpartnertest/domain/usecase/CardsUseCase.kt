package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.domain.model.Card
import io.reactivex.rxjava3.core.Single

interface CardsUseCase{
    fun getCards(activeCard: String) : Single<List<Card>>
}
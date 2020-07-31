package com.platdmit.peterpartnertest.domain.repositories

import com.platdmit.peterpartnertest.domain.model.Card
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CardRepo {
    fun getCard(number: String) : Single<Card>
    fun getCard() : Single<Card>
    fun getCards() : Single<List<Card>>
    fun refreshCards() : Completable
}
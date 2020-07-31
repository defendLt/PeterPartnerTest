package com.platdmit.peterpartnertest.domain.usecase

import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.repositories.CardRepo
import io.reactivex.rxjava3.core.Single

class CardsInteractor(
    private val cardsRepo: CardRepo
) : CardsUseCase {
    override fun getCards(activeCard: String) : Single<List<Card>> {
        return cardsRepo.getCards().map {
            it.map { card ->
                card.select = (card.number == activeCard)
                return@map card
            }
        }
    }
}
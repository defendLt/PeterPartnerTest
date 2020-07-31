package com.platdmit.peterpartnertest.data.converters

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.room.entity.DbCard
import com.platdmit.peterpartnertest.domain.converters.CardConverter
import com.platdmit.peterpartnertest.domain.enums.CardLogoType
import com.platdmit.peterpartnertest.domain.model.Card
import javax.inject.Inject

class CardConverterImpl
@Inject
constructor() : CardConverter<ApiCard, DbCard, Card> {
    override fun fromApiToDb(apiModel: ApiCard): DbCard {
        return DbCard(
            apiModel.card_number,
            apiModel.type,
            apiModel.cardholder_name,
            apiModel.valid,
            apiModel.balance
        )
    }

    override fun fromDbToDomain(dbCard: DbCard): Card {
        return Card(
            dbCard.cardNumber,
            checkCardType(dbCard.type),
            dbCard.cardholderName,
            dbCard.valid,
            dbCard.balance
        )
    }

    private fun checkCardType(type: String): CardLogoType {
        return when (type) {
            "mastercard" -> CardLogoType.MASTERCARD
            "visa" -> CardLogoType.VISA
            "unionpay" -> CardLogoType.UNIONPAY
            else -> CardLogoType.UNKNOWN
        }
    }
}
package com.platdmit.peterpartnertest.data.converters

import com.platdmit.peterpartnertest.data.retrofit.models.ApiTransaction
import com.platdmit.peterpartnertest.data.room.entity.DbTransaction
import com.platdmit.peterpartnertest.domain.converters.TransactionConverter
import com.platdmit.peterpartnertest.domain.model.Transaction
import javax.inject.Inject

class TransactionConverterImpl
@Inject
constructor() :
    TransactionConverter<ApiTransaction, DbTransaction, Transaction> {
    override fun fromApiToDb(apiModel: ApiTransaction, cardNumber : String): DbTransaction {
        return DbTransaction(
            apiModel.title,
            apiModel.icon_url,
            apiModel.date,
            apiModel.amount.toDouble(),
            cardNumber,
            null
        )
    }

    override fun fromDbToDomain(dbModel: DbTransaction): Transaction {
        return Transaction(
            dbModel.name,
            dbModel.icon,
            dbModel.date,
            dbModel.amount
        )
    }
}
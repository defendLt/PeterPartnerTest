package com.platdmit.peterpartnertest.data.converters

import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrency
import com.platdmit.peterpartnertest.data.room.entity.DbCurrency
import com.platdmit.peterpartnertest.domain.converters.CurrencyConverter
import com.platdmit.peterpartnertest.domain.enums.CurrencyType
import com.platdmit.peterpartnertest.domain.model.Currency
import javax.inject.Inject

class CurrencyConverterImpl
@Inject
constructor() :
    CurrencyConverter<ApiCurrency, DbCurrency, Currency> {
    override fun fromApiToDb(apiModel: ApiCurrency): DbCurrency {
        return DbCurrency(
            apiModel.charCode,
            apiModel.name,
            apiModel.value
        )
    }

    override fun fromDbToDomain(dbModel: DbCurrency): Currency {
        return Currency(
            dbModel.charCode,
            checkCurrencyType(dbModel.charCode),
            dbModel.name,
            dbModel.value
        )
    }

    private fun checkCurrencyType(charCode: String): CurrencyType {
        return when (charCode) {
            "GBP" -> CurrencyType.GBP
            "EUR" -> CurrencyType.EUR
            "USD" -> CurrencyType.USD
            else -> CurrencyType.UNKNOWN
        }
    }
}
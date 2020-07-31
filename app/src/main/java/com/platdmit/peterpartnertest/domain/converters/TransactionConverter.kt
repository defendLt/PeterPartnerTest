package com.platdmit.peterpartnertest.domain.converters

interface TransactionConverter<ApiModel, DbModel, DomainModel> {
    fun fromApiToDb(apiModel: ApiModel, cardNumber: String) : DbModel
    fun fromDbToDomain(dbModel: DbModel) : DomainModel
}
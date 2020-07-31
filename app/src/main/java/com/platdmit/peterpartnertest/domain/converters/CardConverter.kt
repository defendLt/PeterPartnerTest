package com.platdmit.peterpartnertest.domain.converters

interface CardConverter<ApiModel, DbModel, DomainModel> {
    fun fromApiToDb(apiModel: ApiModel) : DbModel
    fun fromDbToDomain(dbModel: DbModel) : DomainModel
}
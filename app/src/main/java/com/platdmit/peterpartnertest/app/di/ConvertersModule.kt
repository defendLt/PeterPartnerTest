package com.platdmit.peterpartnertest.app.di

import com.platdmit.peterpartnertest.data.converters.CardConverterImpl
import com.platdmit.peterpartnertest.data.converters.TransactionConverterImpl
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.room.entity.DbCard
import com.platdmit.peterpartnertest.domain.converters.CardConverter
import com.platdmit.peterpartnertest.domain.model.Card
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ConvertersModule {

}
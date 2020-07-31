package com.platdmit.peterpartnertest.app.di

import com.platdmit.peterpartnertest.data.retrofit.ApiCardRepoImpl
import com.platdmit.peterpartnertest.data.retrofit.ApiCurrencyRepoImpl
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCurrency
import com.platdmit.peterpartnertest.data.retrofit.rest.RestCard
import com.platdmit.peterpartnertest.data.retrofit.rest.RestCurrency
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCardRepo
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCurrencyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideOkHttpLogLevel() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpInstance(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor{
                val newRequest = it.request()
                    .newBuilder()
                    .build()
                it.proceed(newRequest)
            }
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideRestCard(
        retrofit: Retrofit.Builder
    ) : RestCard {
        return retrofit
            .baseUrl("https://hr.peterpartner.net/")
            .build()
            .create(RestCard::class.java)
    }

    @Singleton
    @Provides
    fun provideRestCurrency(
        retrofit: Retrofit.Builder
    ) : RestCurrency {
        return retrofit
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .build()
            .create(RestCurrency::class.java)
    }

    @Provides
    fun provideApiCardRepo(
        restCard: RestCard
    ) : ApiCardRepo<ApiCard> {
        return ApiCardRepoImpl(
            restCard
        )
    }

    @Provides
    fun provideApiCurrencyRepo(
        restCurrency: RestCurrency
    ) : ApiCurrencyRepo<ApiCurrency> {
        return ApiCurrencyRepoImpl(
            restCurrency
        )
    }
}
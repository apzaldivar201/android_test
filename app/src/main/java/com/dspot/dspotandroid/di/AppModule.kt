package com.dspot.dspotandroid.di

import com.dspot.dspotandroid.data.network.ApiInterface
import com.dspot.dspotandroid.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideRetrofitInstance(BASE_URL: String): ApiInterface {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(5, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(5, TimeUnit.SECONDS)
        okHttpClient.readTimeout(5, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(5, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

}
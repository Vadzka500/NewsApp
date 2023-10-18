package com.example.newstestapp.di

import com.example.newstestapp.data.api.NewsService
import com.example.newstestapp.utils.Constants.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun baseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideClient() = OkHttpClient.Builder()
        .connectTimeout(15,TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(base_url : String, gson: Gson, client : OkHttpClient): NewsService =
        Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build().create(NewsService::class.java)



}

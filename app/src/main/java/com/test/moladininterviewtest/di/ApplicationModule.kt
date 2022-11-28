package com.test.moladininterviewtest.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.test.moladininterviewtest.BuildConfig
import com.test.moladininterviewtest.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Interceptor", message)
            }
        })
        if (BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        val httpBuilder = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                val request = requestBuilder.build()
                chain.proceed(request)
            }

        httpBuilder.addInterceptor(loggingInterceptor)
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DispatcherIO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DispatcherMain

    @DispatcherIO
    @Provides
    fun provideIO() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DispatcherMain
    @Provides
    fun provideMain() : CoroutineDispatcher {
        return Dispatchers.Main
    }
}
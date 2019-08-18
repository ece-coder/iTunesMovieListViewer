package com.tff.dev.appetiserapp.di.module

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//--------------------------------------------------------------------
// Retrofit and OKHttp are the libraries call for making Http request.
//
// Creating modules for Dagger 2
//--------------------------------------------------------------------

@Module
class NetModule(val baseUrl: String){


    @Provides
    @Singleton
    fun provideHttpCache (application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(application.cacheDir, cacheSize)
        return cache
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return  Interceptor {
            val url = it
                .request()
                .url()
                .newBuilder()
                //.addQueryParameter("format","json")
                .build()

            val request =  it.request()
                .newBuilder()
                .url(url)
                .build()

            it.proceed(request)
        }
    }





}
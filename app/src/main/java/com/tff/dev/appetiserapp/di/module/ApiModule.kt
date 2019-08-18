package com.tff.dev.appetiserapp.di.module

import com.tff.dev.appetiserapp.api.ITunesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

//------------------------------------------------------------------
// Initializing ITunesApi instance for dependency.
// Singleton pattern is used to make sure there's only one instance.
//------------------------------------------------------------------

@Module
class ApiModule{

    @Provides
    @Singleton
    fun providesITunesApi(retrofit: Retrofit): ITunesApi{
        return retrofit.create<ITunesApi>(ITunesApi::class.java)
    }
}
package com.tff.dev.appetiserapp.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.tff.dev.appetiserapp.room.AppDataBase
import com.tff.dev.appetiserapp.room.ItunesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//----------------------------------------------------------------------
// For Persistence, SharePreferences and Room db are used to store data.
//
// Creating modules for Dagger 2
//----------------------------------------------------------------------

@Module
class AppModule(val application: Application){
    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreference() : SharedPreferences = application.getSharedPreferences("shared pref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesAppDatabase(): AppDataBase =
        Room.databaseBuilder(application.applicationContext, AppDataBase::class.java, "itunes-db").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun providesToDoDao(database: AppDataBase): ItunesDao{
      return  database.itunesDao()
    }
}
package com.tff.dev.appetiserapp.di.module

import com.tff.dev.appetiserapp.api.ITunesApi
import com.tff.dev.appetiserapp.repository.ITunesRepository
import com.tff.dev.appetiserapp.room.ItunesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//--------------------------------------
// Initializing ITunesRepository module.
//
// Creating a module for Dagger 2
//--------------------------------------

@Module
class RepoModule{

    @Provides
    @Singleton
    fun providesItunesRepo (api: ITunesApi, dao: ItunesDao): ITunesRepository {
        return ITunesRepository(api, dao)
    }

}
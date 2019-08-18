package com.tff.dev.appetiserapp.di

import android.app.Application
import com.tff.dev.appetiserapp.di.module.ApiModule
import com.tff.dev.appetiserapp.di.module.AppModule
import com.tff.dev.appetiserapp.di.module.NetModule
import com.tff.dev.appetiserapp.di.module.RepoModule
import com.tff.dev.appetiserapp.ituneslist.ItunesListViewModel
import com.tff.dev.appetiserapp.room.ItunesDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(AppModule::class, NetModule::class, ApiModule::class, RepoModule::class)
)

//---------------------------------------------------
// Daggger 2 implementation for Dependency Injection
//---------------------------------------------------

interface AppComponent{


    fun inject(viewModel: ItunesListViewModel)

    companion object Factory{
        fun create(app: Application, baseUrl: String): AppComponent {
            val appComponent = DaggerAppComponent.builder().
                appModule(AppModule(app)).
                apiModule(ApiModule()).
                netModule(NetModule(baseUrl)).
                repoModule(RepoModule()).
                build()
            return appComponent
        }

    }

}

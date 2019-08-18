package com.tff.dev.appetiserapp

import android.app.Application
import com.tff.dev.appetiserapp.di.AppComponent


class App : Application(){

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent =  AppComponent.create(this,"https://itunes.apple.com/")
    }

}
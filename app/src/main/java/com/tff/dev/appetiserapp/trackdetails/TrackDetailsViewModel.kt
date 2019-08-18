package com.tff.dev.appetiserapp.trackdetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.tff.dev.appetiserapp.room.ItunesData

class TrackDetailsViewModel : ViewModel(){

    companion object {
        fun create(activity: AppCompatActivity): TrackDetailsViewModel {
            var itunesViewModel = ViewModelProviders.of(activity).get(TrackDetailsViewModel::class.java)
            return  itunesViewModel
        }
    }


    var itunesData: ItunesData? = null
    /**
     * Set the ItunesData to be displayed
     *
     * @param itunesData the data to be displayed
     */
    fun setData(itunesData: ItunesData){
        this.itunesData = itunesData
    }
}
package com.tff.dev.appetiserapp.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.ConnectivityManager
import com.tff.dev.appetiserapp.room.ItunesData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

//----------------------------------------------------
// This object contains helper functions and variables
//----------------------------------------------------


object Helper {

    // This will be used to store temporay ItunesData when displaying Movie Details (TrackDetailsActivity)

    var trackDetails = ItunesData("","","","","", "",-1,  "", "")


    /**
     * Check if there's an internet connection
     *
     * @return true if there's internet connection and false if none
     */

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * Get the current date and time
     *
     * @return date and time string
     */

    fun getDate(): String{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        return currentDate
    }
}
package com.tff.dev.appetiserapp.api

import com.tff.dev.appetiserapp.data.ItunesListResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//--------------------------------------------------------------------
// This the interface for API calls using RxJava via Observer Pattern.
//--------------------------------------------------------------------

interface ITunesApi {

    /**
     * Http Request
     *
     * Get data from the iTunes API
     *
     * @param term
     * @param country
     * @param media
     * @param all
     *
     */

    @GET("/search")
    fun searchItunesRxjava(@Query("term") term: String,
                     @Query("country") country: String,
                     @Query("media") media: String,
                     @Query("all")  all: String): Observable<ItunesListResponse?>

}

package com.tff.dev.appetiserapp.ituneslist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.tff.dev.appetiserapp.data.ItunesListResponse
import com.tff.dev.appetiserapp.repository.ITunesRepository
import com.tff.dev.appetiserapp.room.ItunesData
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ItunesListViewModel : ViewModel(){

    companion object {
        fun create(activity: AppCompatActivity): ItunesListViewModel {
            var itunesViewModel = ViewModelProviders.of(activity).get(ItunesListViewModel::class.java)
            return  itunesViewModel
        }
    }

    private lateinit var  iTunesRepo: ITunesRepository

    @Inject
    fun init (iTunesRepo: ITunesRepository){
        this.iTunesRepo = iTunesRepo
    }


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    /**
     * Get iTunes List using Http request
     *
     * @return Observable iTunesListResponse data
     */

    fun getItunesListRx(): Observable<ItunesListResponse?>{
        return iTunesRepo.getItunesList().debounce(400, TimeUnit.MILLISECONDS)
    }

    /**
     * Merged the iTunesList from Http request and from the database
     *
     * @return Observable list of ItunesData
     */

    fun getItunesFinalList(input: ItunesListResponse?): Observable<List<ItunesData>>{
        return iTunesRepo.getItunesConcat(input)
    }


    /**
     * Check if the database is empty
     *
     * @return true if the database is empty and false if not
     */

    fun checkIfDbIsEmpty(): Boolean{
        return iTunesRepo.checkIfDBisEmpty()
    }

    /**
     * Update the specific lastAccess (the date and time) property of the database
     *
     * @param track use as reference on which item will be updated
     * @param date the date and time value
     */

    fun updateDb(track: ItunesData, date: String){
        iTunesRepo.updateDate(track, date)
    }

    /**
     * Get a specific ItunesData object based on the position (counter)
     *
     * @param position the position of the wanted ItunesData in the database
     * @return an ItunesData object from the database
     */

    fun getItuneTrack(position: Int): ItunesData?{
        return iTunesRepo.getSingleTrackDetails(position)

    }


    /**
     * Store the current screen reference number internally using SharedPreference
     *
     * @param input the position or reference number
     */

    fun sotrePageRef(input: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("pageNum", input)
        editor.apply()
    }

    /**
     * Get the stored latest screen reference number using SharedPreference
     *
     * @return the stored position or screen reference number
     */

    fun getPageRef(): Int{
        return sharedPreferences.getInt("pageNum", 0)
    }


}
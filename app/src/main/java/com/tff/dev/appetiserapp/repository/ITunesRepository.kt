package com.tff.dev.appetiserapp.repository

import com.tff.dev.appetiserapp.api.ITunesApi
import com.tff.dev.appetiserapp.data.ItunesListResponse
import com.tff.dev.appetiserapp.room.ItunesDao
import com.tff.dev.appetiserapp.room.ItunesData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * A Repository to provide persistence effectively using Room database.
 * It is used to handle data from database and API calls.
 */

class ITunesRepository (val api: ITunesApi, val dao: ItunesDao) {

    /**
     * Get list of iTunes movie using Http request
     *
     * @return Observable list of iTunes movie
     */

    fun getItunesList(): Observable<ItunesListResponse?> {
        return api.searchItunesRxjava("star", "au", "movie", "")
            .doOnNext {

            }
    }

    /**
     * Get list of iTunes movie stored in the database
     *
     * @return Observable list of iTunes movie
     */

    private fun getItunesListFromDb(): Observable<List<ItunesData>> {
        return dao.getMovies().filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {

            }.doOnError {

            }
    }

    /**
     * Concatenates Observable arrays from Http request and database
     *
     * @param input list of iTunes movie from Http request
     * @return concatenated Obsevable list of iTunes movie
     */

    fun getItunesConcat(input: ItunesListResponse?): Observable<List<ItunesData>> {

        if (input != null) {
            return Observable.concatArrayEager(
                getItunesListFromDb(),
                dataConvert(input)

            )
        } else {
            return getItunesListFromDb()
        }
    }

    /**
     * Store the movie list in the database
     *
     * @param movies list of iTunes move to be stored
     */

    fun storeMoviesInDb(movies: List<ItunesData>) {
        Observable.fromCallable { dao.insertAllMovies(movies) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    /**
     * Converts the data from the Http request to format that can be stored in the database
     *
     * @param input the response of iTunes Http request
     * @return Observable list of ItunesData that is compatible to the database entries
     */

    fun dataConvert(input: ItunesListResponse?): Observable<List<ItunesData>> {

        var tracks: ArrayList<ItunesData> = arrayListOf()
        if (input != null) {
            var counter = 1
            for (i in input.results) {
                var track = ItunesData("", "", "", "", "", "", -1, "", "")
                if (checkIfExisting(i.trackName)) {
                    track.lastAccess = getLastAccesInfo(i.trackName)
                }
                track.trackName = i.trackName
                track.artwork = i.artworkUrl100
                track.genre = i.primaryGenreName
                track.price = i.trackPrice.toString()
                track.longDescription = i.longDescription
                track.counter = counter
                track.currency = i.currency
                track.contentAdvisoryRating = i.contentAdvisoryRating
                tracks.add(track)
                counter++

            }

            storeMoviesInDb(tracks)
        }
        return Observable.fromArray(tracks)
    }

    /**
     * Checks if the database base is empty
     *
     * @return true if empty and false if not
     */

    fun checkIfDBisEmpty(): Boolean {

        if (dao.getDatabseCount() > 0) {
            return false
        }
        return true
    }


    /**
     * Updates the date and time of last access of a specific entry in
     * the database
     *
     * @param track the ItunesData object to be updated in the database
     * @param date the date and time
     */

    fun updateDate(track: ItunesData, date: String) {
        var iTunes = track
        iTunes.lastAccess = date
        dao.updateList(iTunes)
    }

    /**
     * Checks if a certain movie is existing in the database
     *
     * @param trackName the title of movie to be searched
     * @return true if existing and false if not
     */

    fun checkIfExisting(trackName: String): Boolean {

        if (checkIfDBisEmpty()) {
            return false
        }

        if (dao.loadSingle(trackName).trackName.isNotEmpty()) {
            return true
        }
        return false
    }


    /**
     * Get the date and time of the last acccess of a specific entry
     * based on movie name
     *
     * @param name movie name to be searched in the database
     * @returns date and time of last access
     */

    fun getLastAccesInfo(name: String): String {
        return dao.loadSingle(name).lastAccess
    }


    /**
     * Get movie details via postion
     *
     * @param count position in the database
     * @return the iTunes movie data
     */

    fun getSingleTrackDetails(count: Int): ItunesData? {

        return dao.loadSingleViaCounter(count)

    }


}
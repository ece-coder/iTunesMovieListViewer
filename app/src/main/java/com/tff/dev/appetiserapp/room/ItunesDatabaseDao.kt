package com.tff.dev.appetiserapp.room

import android.arch.persistence.room.*
import io.reactivex.Single


//Database and DAO

@Database(entities = arrayOf(ItunesData::class), version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun itunesDao(): ItunesDao
}


@Dao
interface ItunesDao{

    /**
     * Get all the movies from the database
     *
     * @return the list of iTunes movies from the database
     */

    @Query("SELECT * FROM movies")
    fun getMovies(): Single<List<ItunesData>>


    /**
     * Insert a list of iTunes movies in the database
     *
     * @param tracks list of iTunes movies
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(tracks: List<ItunesData>)


    /**
     * Get the number of entries in the database
     *
     * @return the number movies in the database
     */

    @Query("SELECT COUNT(*) FROM movies")
    fun getDatabseCount(): Int

    /**
     * Update a certain entry in the database
     *
     * @param toAdd variable arguments to be stored in the database
     */

    @Update
    fun updateList(vararg toAdd: ItunesData)

    /**
     * Get a certain entry in the database using the movie title
     *
     * @param name title of the movie
     * @return the specific entry from the database
     */

    @Query("SELECT * FROM movies WHERE trackName = :name ")
    fun loadSingle(name: String): ItunesData

    /**
     * Get a certain entry in the database using their position
     *
     * @param count position in the database
     * @return the specific entry from the database
     */

    @Query("SELECT * FROM movies WHERE counter = :count ")
    fun loadSingleViaCounter(count: Int): ItunesData

}
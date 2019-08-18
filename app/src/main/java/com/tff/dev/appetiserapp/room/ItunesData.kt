package com.tff.dev.appetiserapp.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movies")
data class ItunesData(
    @PrimaryKey
    @ColumnInfo(name = "trackName")
    var trackName: String,
    @ColumnInfo(name = "artwork")
    var artwork: String,
    @ColumnInfo(name = "price")
    var price: String,
    @ColumnInfo(name = "genre")
    var genre: String,
    @ColumnInfo(name = "lastAccess")
    var lastAccess: String,
    @ColumnInfo(name = "longDescription")
    var longDescription: String,
    @ColumnInfo(name = "counter")
    var counter: Int,
    @ColumnInfo(name = "currency")
    var currency: String,
    @ColumnInfo(name = "contentAdvisoryRating")
    var contentAdvisoryRating: String



)
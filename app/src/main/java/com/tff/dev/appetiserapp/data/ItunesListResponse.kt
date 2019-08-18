package com.tff.dev.appetiserapp.data

import com.google.gson.annotations.SerializedName

/**
 * Model used to catch response data after calling iTunes API.
 */

data class ItunesListResponse(@SerializedName("resultCount")
                              var resultCount: Int = 0,
                              @SerializedName("results")
                              var results: List<ItunesTrackModel> = ArrayList()
)
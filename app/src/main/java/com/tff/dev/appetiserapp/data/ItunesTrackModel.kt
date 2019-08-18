package com.tff.dev.appetiserapp.data

import com.google.gson.annotations.SerializedName

data class ItunesTrackModel(@SerializedName("wrapperType")
                            var wrapperType: String = "",
                            @SerializedName("kind")
                            var kind: String = "",
                            @SerializedName("artistId")
                            var artistId: Long = 0,
                            @SerializedName("collectionId")
                            var collectionId: Long = 0,
                            @SerializedName("trackId")
                            var trackId: Long = 0,
                            @SerializedName("artistName")
                            var artistName: String = "",
                            @SerializedName("collectionName")
                            var collectionName: String = "",
                            @SerializedName("trackName")
                            var trackName: String = "",
                            @SerializedName("collectionCensoredName")
                            var collectionCensoredName: String = "",
                            @SerializedName("trackCensoredName")
                            var trackCensoredName: String = "",
                            @SerializedName("artistViewUrl")
                            var artistViewUrl: String = "",
                            @SerializedName("trackViewUrl")
                            var trackViewUrl: String = "",
                            @SerializedName("previewUrl")
                            var previewUrl: String = "",
                            @SerializedName("artworkUrl30")
                            var artworkUrl30: String = "",
                            @SerializedName("artworkUrl60")
                            var artworkUrl60: String = "",
                            @SerializedName("artworkUrl100")
                            var artworkUrl100: String = "",
                            @SerializedName("collectionPrice")
                            var collectionPrice: Double = 0.0,
                            @SerializedName("trackPrice")
                            var trackPrice: Double = 0.0,
                            @SerializedName("releaseDate")
                            var releaseDate: String = "",
                            @SerializedName("collectionExplicitness")
                            var collectionExplicitness: String = "",
                            @SerializedName("trackExplicitness")
                            var trackExplicitness: String = "",
                            @SerializedName("discCount")
                            var discCount: Int = 0,
                            @SerializedName("discNumber")
                            var discNumber: Int = 0,
                            @SerializedName("trackCount")
                            var trackCount: Int = 0,
                            @SerializedName("trackNumber")
                            var trackNumber: Int = 0,
                            @SerializedName("trackTimeMillis")
                            var trackTimeMillis: Long = 0,
                            @SerializedName("country")
                            var country: String = "",
                            @SerializedName("currency")
                            var currency: String = "",
                            @SerializedName("primaryGenreName")
                            var primaryGenreName: String = "",
                            @SerializedName("contentAdvisoryRating")
                            var contentAdvisoryRating: String = "",
                            @SerializedName("hasITunesExtras")
                            var hasITunesExtras: Boolean = false,
                            @SerializedName("longDescription")
                            var longDescription: String = ""

)
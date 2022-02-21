package com.example.forest.network.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AllSerieNetworkEntity(
        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "image")
        val imgUrl: SerieImageUrl?,

        @Json(name = "genres")
        val genres: List<String>,

        @Json(name = "status")
        val status: String?,

        @Json(name = "rating")
        val rating: SerieRating?,

        @Json(name = "summary")
        var summary: String? = null
) : Parcelable {

        @Parcelize
        data class SerieImageUrl(
                @Json(name = "medium")
                val medium: String? = null,

                @Json(name = "original")
                val original: String? = null
        ) : Parcelable

        @Parcelize
        data class SerieRating(
                @Json(name = "average")
                val average: Float? = null,
        ) : Parcelable
}


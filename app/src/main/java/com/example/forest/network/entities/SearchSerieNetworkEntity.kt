package com.example.forest.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchSerieNetworkEntity(
    @Json(name = "score")
    val score: Float? = null,

    @Json(name = "show")
    val show: AllSerieNetworkEntity?
)
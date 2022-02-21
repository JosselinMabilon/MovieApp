package com.example.forest.domain.model

import android.os.Parcelable
import com.example.forest.network.entities.AllSerieNetworkEntity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Serie(
    val id: Int? = null,
    var name: String? = null,
    val imgUrl: @RawValue AllSerieNetworkEntity.SerieImageUrl?,
    val genres: List<String>,
    val status: String?,
    val rating: @RawValue AllSerieNetworkEntity.SerieRating?,
    var summary: String? = null
) : Parcelable

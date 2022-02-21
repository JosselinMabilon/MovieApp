package com.example.forest.domain.model

import android.os.Parcelable
import com.example.forest.network.entities.AllSerieNetworkEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchSerie(
    val score: Float? = null,
    val show: AllSerieNetworkEntity?,
) : Parcelable

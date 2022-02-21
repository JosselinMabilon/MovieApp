package com.example.forest.network

import com.example.forest.network.entities.AllSerieNetworkEntity
import com.example.forest.network.entities.SearchSerieNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface SerieApi {

    @GET("shows")
    suspend fun getAllSerie(
        @Query("page") page: Int
    ): List<AllSerieNetworkEntity>

    @GET("search/shows")
    suspend fun searchSerie(
        @Query("q") query: String?
    ): List<SearchSerieNetworkEntity>
}
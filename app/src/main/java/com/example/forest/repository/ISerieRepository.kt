package com.example.forest.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.forest.domain.model.SearchSerie
import com.example.forest.domain.model.Serie

interface ISerieRepository {

    suspend fun getAllSerie(page: Int): List<Serie>

    suspend fun searchSerie(query: String?): List<SearchSerie>

    fun getSerie(): LiveData<PagingData<Serie>>
}
package com.example.forest.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.forest.domain.model.SearchSerie
import com.example.forest.domain.model.Serie
import com.example.forest.network.SerieApi
import com.example.forest.network.SeriePagingSource
import com.example.forest.network.entities.AllSerieNetworkMapper
import com.example.forest.network.entities.SearchSerieNetworkMapper

private const val PAGE_SIZE = 20
private const val MAX_SIZE = 100

class SerieRepositoryImpl(
    private val serieApi: SerieApi,
    private val mapper: AllSerieNetworkMapper,
    private val mapperSearch: SearchSerieNetworkMapper
    ): ISerieRepository {

    override suspend fun getAllSerie(page: Int): List<Serie> {
        return mapper.fromEntityList(serieApi.getAllSerie(page))
    }

    override suspend fun searchSerie(query: String?): List<SearchSerie> {
        return mapperSearch.fromEntityList(serieApi.searchSerie(query))
    }

    override fun getSerie(): LiveData<PagingData<Serie>> {
        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        maxSize = MAX_SIZE,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { SeriePagingSource(serieApi, mapper) }
        ).liveData
    }
}
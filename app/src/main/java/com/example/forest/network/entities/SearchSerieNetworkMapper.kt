package com.example.forest.network.entities

import com.example.forest.domain.mapper.EntityMapper
import com.example.forest.domain.model.SearchSerie

class SearchSerieNetworkMapper : EntityMapper<SearchSerieNetworkEntity, SearchSerie> {

    override fun mapToDomainModel(model: SearchSerieNetworkEntity): SearchSerie {
        return SearchSerie(
            score = model.score,
            show = model.show
        )
    }

    fun fromEntityList(initial: List<SearchSerieNetworkEntity>) : List<SearchSerie> {
        return initial.map { mapToDomainModel(it) }
    }
}
package com.example.forest.network.entities

import com.example.forest.domain.model.Serie
import com.example.forest.domain.mapper.EntityMapper

class AllSerieNetworkMapper : EntityMapper<AllSerieNetworkEntity, Serie> {

    override fun mapToDomainModel(model: AllSerieNetworkEntity): Serie {
        return Serie(
            id = model.id,
            name = model.name,
            imgUrl = model.imgUrl,
            genres = model.genres,
            status = model.status,
            rating = model.rating,
            summary = model.summary
        )
    }

    fun fromEntityList(initial: List<AllSerieNetworkEntity>) : List<Serie> {
        return initial.map { mapToDomainModel(it) }
    }
}
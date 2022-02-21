package com.example.forest.domain.mapper

interface EntityMapper <Entity, DomainModel> {

    fun mapToDomainModel(model: Entity): DomainModel
}
package com.example.forest.repository

import com.example.forest.domain.model.SearchSerie
import com.example.forest.domain.model.Serie
import com.example.forest.network.SerieApi
import com.example.forest.network.entities.AllSerieNetworkEntity
import com.example.forest.network.entities.AllSerieNetworkMapper
import com.example.forest.network.entities.SearchSerieNetworkEntity
import com.example.forest.network.entities.SearchSerieNetworkMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class SerieRepositoryImplTest() {

    @MockK
    lateinit var serieApi: SerieApi

    @MockK
    lateinit var mapper: AllSerieNetworkMapper

    @MockK
    lateinit var mapperSearch: SearchSerieNetworkMapper

    lateinit var sut: SerieRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = SerieRepositoryImpl(serieApi, mapper, mapperSearch)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testSerieReturnListOfSeries() {
        // Given
        val pageMock = 1
        val mockListSerie = mockk<List<Serie>>()
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()

        coEvery { serieApi.getAllSerie(eq(pageMock)) } returns mockAllSerieNetworkEntity
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: List<Serie>
        runBlocking {
            result = sut.getAllSerie(pageMock)
        }

        // Then
        assertThat(result).isEqualTo(mockListSerie)
        coVerifyAll {
            serieApi.getAllSerie(eq(pageMock))
            mapper.fromEntityList(mockAllSerieNetworkEntity)
        }
    }

    @Test
    fun testSearchSerieReturnListOfSearchSeries() {
        // Given
        val queryMock = "test"
        val mockListSearchSerie = mockk<List<SearchSerie>>()
        val mockSearchSerieNetworkEntity = mockk<List<SearchSerieNetworkEntity>>()

        coEvery { serieApi.searchSerie(eq(queryMock)) } returns mockSearchSerieNetworkEntity
        coEvery { mapperSearch.fromEntityList(mockSearchSerieNetworkEntity) } returns mockListSearchSerie

        // When
        lateinit var result: List<SearchSerie>
        runBlocking {
            result = sut.searchSerie(queryMock)
        }

        // Then
        assertThat(result).isEqualTo(mockListSearchSerie)
        coVerifyAll {
            serieApi.searchSerie(eq(queryMock))
            mapperSearch.fromEntityList(mockSearchSerieNetworkEntity)
        }
    }
}











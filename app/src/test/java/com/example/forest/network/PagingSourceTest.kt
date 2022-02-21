package com.example.forest.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.forest.domain.model.Serie
import com.example.forest.network.entities.AllSerieNetworkEntity
import com.example.forest.network.entities.AllSerieNetworkMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class PagingSourceTest() {

    @MockK
    lateinit var serieApi: SerieApi

    @MockK
    lateinit var mapper: AllSerieNetworkMapper

    lateinit var sut: SeriePagingSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = SeriePagingSource(serieApi, mapper)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testLoadReturnLoadResultIfPositionIsNull() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 1
        val mockSerie = mockk<Serie>()
        val mockListSerie = listOf(mockSerie)
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockNextKey = 2
        val mockPrevKey = null

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } returns mockAllSerieNetworkEntity
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Page<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Page<Int, Serie>
        }

        // Then
        assertThat(result.nextKey).isEqualTo(mockNextKey)
        assertThat(result.prevKey).isEqualTo(mockPrevKey)
        assertThat(result.data).isEqualTo(mockListSerie)
        coVerifyAll {
            serieApi.getAllSerie(eq(positionMock))
            mapper.fromEntityList(mockAllSerieNetworkEntity)
        }
    }

    @Test
    fun testLoadReturnLoadResultIfPositionEqualOne() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 2
        val mockSerie = mockk<Serie>()
        val mockListSerie = listOf(mockSerie)
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockNextKey = 3
        val mockPrevKey = 1

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } returns mockAllSerieNetworkEntity
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Page<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Page<Int, Serie>
        }

        // Then
        assertThat(result.nextKey).isEqualTo(mockNextKey)
        assertThat(result.prevKey).isEqualTo(mockPrevKey)
        assertThat(result.data).isEqualTo(mockListSerie)
        coVerifyAll {
            serieApi.getAllSerie(eq(positionMock))
            mapper.fromEntityList(mockAllSerieNetworkEntity)
        }
    }

    @Test
    fun testLoadReturnLoadResultIfResponseIsEmpty() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 2
        val mockListSerie = emptyList<Serie>()
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockNextKey = null
        val mockPrevKey = 1

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } returns mockAllSerieNetworkEntity
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Page<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Page<Int, Serie>
        }

        // Then
        assertThat(result.nextKey).isEqualTo(mockNextKey)
        assertThat(result.prevKey).isEqualTo(mockPrevKey)
        assertThat(result.data).isEqualTo(mockListSerie)
        coVerifyAll {
            serieApi.getAllSerie(eq(positionMock))
            mapper.fromEntityList(mockAllSerieNetworkEntity)
        }
    }

    @Test
    fun testLoadReturnLoadResultIfResponseIsEmptyAndIfPositionIsNull() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 1
        val mockListSerie = emptyList<Serie>()
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockNextKey = null
        val mockPrevKey = null

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } returns mockAllSerieNetworkEntity
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Page<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Page<Int, Serie>
        }

        // Then
        assertThat(result.nextKey).isEqualTo(mockNextKey)
        assertThat(result.prevKey).isEqualTo(mockPrevKey)
        assertThat(result.data).isEqualTo(mockListSerie)
        coVerifyAll {
            serieApi.getAllSerie(eq(positionMock))
            mapper.fromEntityList(mockAllSerieNetworkEntity)
        }
    }

    @Test
    fun testLoadReturnLoadResultErrorIOException() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 1
        val mockListSerie = emptyList<Serie>()
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockIOException = mockk<IOException>()

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } throws mockIOException
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Error<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Error<Int, Serie>
        }

        // Then
        assertThat(result.throwable).isEqualTo(mockIOException)
        coVerify {
            serieApi.getAllSerie(eq(positionMock))
        }
    }

    @Test
    fun testLoadReturnLoadResultErrorHttpException() {
        // Given
        val mockParams = mockk<PagingSource.LoadParams<Int>>()
        val positionMock = 1
        val mockListSerie = emptyList<Serie>()
        val mockAllSerieNetworkEntity = mockk<List<AllSerieNetworkEntity>>()
        val mockHttpException = mockk<HttpException>()

        every { mockParams.key } returns positionMock
        coEvery { serieApi.getAllSerie(eq(positionMock)) } throws mockHttpException
        coEvery { mapper.fromEntityList(mockAllSerieNetworkEntity) } returns mockListSerie

        // When
        lateinit var result: PagingSource.LoadResult.Error<Int, Serie>
        runBlocking {
            result = sut.load(mockParams) as PagingSource.LoadResult.Error<Int, Serie>
        }

        // Then
        assertThat(result.throwable).isEqualTo(mockHttpException)
        coVerify {
            serieApi.getAllSerie(eq(positionMock))
        }
    }

    @Test
    fun testGetRefreshKeyReturnInt() {
        // Given
        val mockState = mockk<PagingState<Int, Serie>>()
        val mockAnchorPosition = 1

        every { mockState.anchorPosition } returns mockAnchorPosition

        // When
        val result = sut.getRefreshKey(mockState)

        // Then
        assertThat(result).isEqualTo(mockAnchorPosition)
        coVerify {
            mockState.anchorPosition
        }
    }

    @Test
    fun testGetRefreshKeyReturnNull() {
        // Given
        val mockState = mockk<PagingState<Int, Serie>>()
        val mockAnchorPosition = null

        every { mockState.anchorPosition } returns mockAnchorPosition

        // When
        val result = sut.getRefreshKey(mockState)

        // Then
        assertThat(result).isEqualTo(mockAnchorPosition)
        coVerify {
            mockState.anchorPosition
        }
    }
}
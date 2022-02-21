package com.example.forest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.forest.domain.model.SearchSerie
import com.example.forest.repository.ISerieRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class SearchViewModelTest() {

    @MockK
    lateinit var repository: ISerieRepository

    @MockK
    lateinit var state: SavedStateHandle

    lateinit var sut: SearchViewModel

    @ExperimentalCoroutinesApi
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        sut = SearchViewModel(repository, state)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testChangeQuery() {
        val queryMock = "test"

        sut.changeQuery(queryMock)

        assertThat(sut.currentQuery.value).isEqualTo(queryMock)
    }

    @Test
    fun testSearchSerieReturnListSearchSerie() {
        val errorMock = null
        val currentQueryMock = mockk<MutableLiveData<String>>()
        val listSearchSerieMock = mockk<List<SearchSerie>>()

        coEvery { repository.searchSerie(currentQueryMock.value) } returns listSearchSerieMock

        runBlocking {
            sut.searchSerie()
        }

        assertThat(sut._series.value).isEqualTo(listSearchSerieMock)
        assertThat(sut.serie.value).isEqualTo(listSearchSerieMock)
        assertThat(sut._errorState.value).isEqualTo(errorMock)
        assertThat(sut.errorState.value).isEqualTo(errorMock)
        coVerify(exactly = 1) { repository.searchSerie(any()) }
    }

    @Test
    fun testCallServiceApiReturnHttpException() {
        val currentQueryMock = mockk<MutableLiveData<String>>()
        val mockHttpException = mockk<IOException>()

        coEvery { repository.searchSerie(currentQueryMock.value) } throws mockHttpException

        runBlocking {
            sut.searchSerie()
        }

        assertThat(sut._errorState.value).isEqualTo(mockHttpException.toString())
        assertThat(sut.errorState.value).isEqualTo(mockHttpException.toString())
        coVerify(exactly = 1) { repository.searchSerie(any()) }
    }
}
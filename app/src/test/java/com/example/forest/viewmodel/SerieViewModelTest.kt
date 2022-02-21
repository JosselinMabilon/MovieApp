package com.example.forest.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.forest.domain.model.Serie
import com.example.forest.repository.ISerieRepository
import com.example.forest.repository.ISharedPreferences
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.forest.viewmodel.SerieViewModel.Companion.SWITCH_DM_BOOLEAN_KEY

class SerieViewModelTest() {

    @MockK
    lateinit var repository: ISerieRepository

    @MockK
    lateinit var sharedPreferences: ISharedPreferences

    lateinit var sut: SerieViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = SerieViewModel(repository, sharedPreferences)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetPagedReSerieturnLiveDataPagingDataSerie() {
        val mockPagedSerie = spyk<LiveData<PagingData<Serie>>>()
        val filterQueryMock = null

        coEvery { repository.getSerie() } returns mockPagedSerie

        lateinit var result: LiveData<PagingData<Serie>>
        runBlocking {
            result = sut.getPagedSerie(filterQueryMock)
        }

        assertThat(result.value).isEqualTo(mockPagedSerie.value)
        coVerify { repository.getSerie() }
    }

    @Test
    fun testGetPagedReSerieturnLiveDataPagingDataSerieWithFilterQueryNotNull() {
        val mockPagedSerie = spyk<LiveData<PagingData<Serie>>>()
        val filterQueryMock = "Running"

        coEvery { repository.getSerie() } returns mockPagedSerie

        lateinit var result: LiveData<PagingData<Serie>>
        runBlocking {
            result = sut.getPagedSerie(filterQueryMock)
        }

        assertThat(result.value).isEqualTo(mockPagedSerie.value)
        coVerify { repository.getSerie() }
    }

    @Test
    fun testGetPagedSerieReturnEmptyListWithFilterQueryNull() {
        val mockPagedSerie = spyk<LiveData<PagingData<Serie>>>()
        val emptyPagedSerie = PagingData.empty<Serie>()
        val filterQueryMock = null

        coEvery { repository.getSerie() } returns mockPagedSerie
        coEvery { mockPagedSerie.value } returns emptyPagedSerie

        lateinit var result: LiveData<PagingData<Serie>>
        runBlocking {
            result = sut.getPagedSerie(filterQueryMock)
        }

        assertThat(result.value).isNull()
        coVerify { repository.getSerie() }
    }

    @Test
    fun testLoadDarkModeDataReturnBooleanWithFalseInDefaultValue() {
        val defaultValueMock = false
        val sharedPrefReturnValueMock = false

        every { sharedPreferences.getBooleanValue(
            eq(SWITCH_DM_BOOLEAN_KEY), eq(defaultValueMock)) } returns sharedPrefReturnValueMock

        val result: Boolean = sut.loadDarkModeData()

        assertThat(result).isEqualTo(sharedPrefReturnValueMock)
        verify { sharedPreferences.getBooleanValue(eq(SWITCH_DM_BOOLEAN_KEY), eq(defaultValueMock)) }
    }
}
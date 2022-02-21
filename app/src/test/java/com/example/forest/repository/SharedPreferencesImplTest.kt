package com.example.forest.repository

import android.content.SharedPreferences
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class SharedPreferencesImplTest() {

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    lateinit var sut: SharedPreferencesImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = SharedPreferencesImpl(sharedPreferences)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetBooleanValueReturnBooleanWithFalseAsDefaultValue() {
        val keyMock = "test"
        val defaultValueMock = false
        val sharedPrefReturnValue = false

        every { sharedPreferences.getBoolean(eq(keyMock), eq(defaultValueMock)) } returns sharedPrefReturnValue

        val result: Boolean = sut.getBooleanValue(keyMock, defaultValueMock)

        assertThat(result).isEqualTo(sharedPrefReturnValue)
        verify { sharedPreferences.getBoolean(eq(keyMock), eq(defaultValueMock)) }
    }

    @Test
    fun testGetBooleanValueReturnBooleanWithTrueAsDefaultValue() {
        val keyMock = "test"
        val defaultValueMock = true
        val sharedPrefReturnValue = false

        every { sharedPreferences.getBoolean(eq(keyMock), eq(defaultValueMock)) } returns sharedPrefReturnValue

        val result: Boolean = sut.getBooleanValue(keyMock, defaultValueMock)

        assertThat(result).isEqualTo(sharedPrefReturnValue)
        verify { sharedPreferences.getBoolean(eq(keyMock), eq(defaultValueMock)) }
    }

    @Test
    fun testPutBooleanValue() {
        val keyMock = "test"
        val valueMock = false
        val editorMock = mockk<SharedPreferences.Editor>()

        every { sharedPreferences.edit() } returns editorMock
        every { editorMock.putBoolean(eq(keyMock), eq(valueMock)) } returns editorMock
        every { editorMock.apply() } returns Unit

        sut.putBooleanValue(keyMock, valueMock)

        verifyAll {
            sharedPreferences.edit()
            editorMock.putBoolean(eq(keyMock), eq(valueMock))
            editorMock.apply()
        }
    }
}
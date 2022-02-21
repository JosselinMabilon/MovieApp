package com.example.forest.repository

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.forest.repository.provider.EncryptedSharedPreferencesProvider
import com.example.forest.repository.provider.SECURE_STORAGE_FILENAME
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class EncryptedSharedPreferencesProviderTest() {

    @MockK
    lateinit var context: Context

    lateinit var sut: EncryptedSharedPreferencesProvider

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = EncryptedSharedPreferencesProvider(context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetFunctionReturnSharedPreferences() {
        mockkStatic(MasterKeys::class)
        mockkStatic(EncryptedSharedPreferences::class)
        val mockKeyGen = mockk<KeyGenParameterSpec>()
        val mockMasterKey = "MasterKey"
        val mockSharedPreferences = mockk<SharedPreferences>()

        every { MasterKeys.AES256_GCM_SPEC } returns mockKeyGen
        every { MasterKeys.getOrCreate(any()) } returns mockMasterKey
        every { context.applicationContext } returns context
        every { EncryptedSharedPreferences.create(
            eq(SECURE_STORAGE_FILENAME),
            eq(mockMasterKey),
            eq(context),
            any(),
            any()
        ) } returns mockSharedPreferences

        val result = sut.get()

        assertThat(result).isEqualTo(mockSharedPreferences)
    }
}
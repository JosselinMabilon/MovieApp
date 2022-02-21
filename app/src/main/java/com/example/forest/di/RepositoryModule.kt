package com.example.forest.di

import android.content.Context
import com.example.forest.network.SerieApi
import com.example.forest.network.entities.AllSerieNetworkMapper
import com.example.forest.network.entities.SearchSerieNetworkMapper
import com.example.forest.repository.ISerieRepository
import com.example.forest.repository.ISharedPreferences
import com.example.forest.repository.SerieRepositoryImpl
import com.example.forest.repository.SharedPreferencesImpl
import com.example.forest.repository.provider.EncryptedSharedPreferencesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSerieRepository(
        serieService: SerieApi,
        allSerieNetworkMapper: AllSerieNetworkMapper,
        searchSerieNetworkMapper: SearchSerieNetworkMapper
    ): ISerieRepository {
        return SerieRepositoryImpl(serieService, allSerieNetworkMapper, searchSerieNetworkMapper)
    }

    @Singleton
    @Provides
    fun provideEncryptSharedPreferencesProvider(
        @ApplicationContext context: Context
    ) : EncryptedSharedPreferencesProvider {
        return EncryptedSharedPreferencesProvider(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(
        sharedPreferences: EncryptedSharedPreferencesProvider
    ): ISharedPreferences {
        return SharedPreferencesImpl(sharedPreferences.get())
    }
}
package com.example.forest.di

import com.example.forest.BuildConfig
import com.example.forest.network.SerieApi
import com.example.forest.network.entities.AllSerieNetworkMapper
import com.example.forest.network.entities.SearchSerieNetworkMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAllSerieMapper() : AllSerieNetworkMapper {
        return AllSerieNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideSearchSerieMapper() : SearchSerieNetworkMapper {
        return SearchSerieNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideSerieService() : SerieApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build())
            )
            .build()
            .create(SerieApi::class.java)
    }
}
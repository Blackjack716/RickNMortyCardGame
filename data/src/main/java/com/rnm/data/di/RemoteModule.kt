package com.rnm.data.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.rnm.data.remote.api.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(API_RICK_AND_MORTY)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(RickAndMortyApi::class.java)
    }

    companion object {
        const val API_RICK_AND_MORTY = "https://rickandmortyapi.com/"
    }

}



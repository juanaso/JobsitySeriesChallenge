package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class SeriesListModule {

    @Provides
    fun seriesListAPI(retrofit: Retrofit)  = retrofit.create(SeriesAPI::class.java)


    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl("https://api.tvmaze.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
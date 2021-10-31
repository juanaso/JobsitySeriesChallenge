package com.juanasoapp.jobsityserieschallenge.serieslist

import com.jakewharton.espresso.OkHttp3IdlingResource
import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var client = OkHttpClient()
var idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class SeriesListModule {

    @Provides
    fun seriesListAPI(retrofit: Retrofit) = retrofit.create(SeriesAPI::class.java)

    @Provides
    fun retrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)


        return Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
package com.juanasoapp.jobsityserieschallenge

import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import retrofit2.http.GET

interface SeriesAPI {
    @GET("shows")
    suspend fun fetchAllSeriesList(): List<Series>

}

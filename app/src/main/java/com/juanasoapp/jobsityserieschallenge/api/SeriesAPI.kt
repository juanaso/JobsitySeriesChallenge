package com.juanasoapp.jobsityserieschallenge.api

import com.juanasoapp.jobsityserieschallenge.seriesdetail.model.Episode
import com.juanasoapp.jobsityserieschallenge.serieslist.model.Series
import com.juanasoapp.jobsityserieschallenge.serieslist.SeriesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesAPI {
    @GET("shows")
    suspend fun fetchAllSeriesList(): List<Series>

    @GET("search/shows")
    suspend fun fetchWithQuerySeriesList(@Query("q") testString: String): SeriesSearchResponse

    @GET("shows/{id}/episodes")
    suspend fun fetchEpisodes(@Path("id") id: String): List<Episode>
}

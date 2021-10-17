package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SeriesListRepository(
    private val service : SeriesListService
) {

    suspend fun getSeriesList(): Flow<Result<List<Series>>> =
        service.fetchSeriesList()


}

package com.juanasoapp.jobsityserieschallenge.serieslist.api

import com.juanasoapp.jobsityserieschallenge.serieslist.model.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesListRepository @Inject constructor(
    private val service: SeriesListService
) {

    suspend fun getSeriesList(currentQuery: String = ""): Flow<Result<List<Series>>> {
        return if (currentQuery.isBlank()) {
            service.fetchSeriesList()
        } else {
            service.fetchQuerySeriesList(currentQuery)
        }
    }
}

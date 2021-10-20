package com.juanasoapp.jobsityserieschallenge.serieslist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SeriesListRepository @Inject constructor(
    private val service : SeriesListService
) {

    suspend fun getSeriesList(currentQuery: String= ""): Flow<Result<List<Series>>> {
        return if(currentQuery.isBlank()){
            service.fetchSeriesList()
        }else{
            service.fetchQuerySeriesList(currentQuery)
        }
    }


}

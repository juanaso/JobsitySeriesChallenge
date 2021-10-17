package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import kotlinx.coroutines.flow.Flow


class SeriesListRepository {

    suspend fun getSeriesList(): Flow<Result<List<Series>>> {
        TODO("Not yet implemented")
    }

}

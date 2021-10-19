package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.seriesdetail.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeriesDetailRepository @Inject constructor(
    private val service :SeriesDetailService
) {

    suspend fun getEpisodes():Flow<Result<List<Episode>>>  {
        return service.fetchEpisodes()
    }
}

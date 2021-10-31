package com.juanasoapp.jobsityserieschallenge.seriesdetail

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesDetailRepository @Inject constructor(
    private val service: SeriesDetailService
) {

    fun getEpisodes(id: String): Flow<Result<List<Episode>>> {
        return service.fetchEpisodes(id)
    }
}

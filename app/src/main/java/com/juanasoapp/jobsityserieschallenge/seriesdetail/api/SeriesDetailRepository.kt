package com.juanasoapp.jobsityserieschallenge.seriesdetail.api

import com.juanasoapp.jobsityserieschallenge.seriesdetail.model.Episode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesDetailRepository @Inject constructor(
    private val service: SeriesDetailService
) {

    fun getEpisodes(id: String): Flow<Result<List<Episode>>> {
        return service.fetchEpisodes(id)
    }
}

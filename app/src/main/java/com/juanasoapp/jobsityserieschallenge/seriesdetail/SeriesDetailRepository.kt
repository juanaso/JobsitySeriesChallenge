package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.seriesdetail.Episode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesDetailRepository @Inject constructor() {

    fun getEpisodes():Flow<Result<List<Episode>>>  {
        TODO("Not yet implemented")
    }
}

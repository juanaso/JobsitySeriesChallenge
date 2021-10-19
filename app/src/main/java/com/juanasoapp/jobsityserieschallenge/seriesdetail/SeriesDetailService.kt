package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesDetailService @Inject constructor(){

    fun fetchEpisodes(): Flow<Result<List<Episode>>> {
        TODO("Not yet implemented")
    }

}

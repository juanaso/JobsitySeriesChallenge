package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class SeriesDetailService @Inject constructor(
    private var api : SeriesAPI
    ){

    fun fetchEpisodes(id: String): Flow<Result<List<Episode>>> {
        return flow{
            emit(Result.success(api.fetchEpisodes(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}

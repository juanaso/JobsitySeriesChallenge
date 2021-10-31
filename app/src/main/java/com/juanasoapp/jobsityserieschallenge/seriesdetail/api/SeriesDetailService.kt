package com.juanasoapp.jobsityserieschallenge.seriesdetail.api

import com.juanasoapp.jobsityserieschallenge.api.SeriesAPI
import com.juanasoapp.jobsityserieschallenge.seriesdetail.model.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class SeriesDetailService @Inject constructor(
    private var api: SeriesAPI
) {
    private val errorMessage = "Something went wrong"

    fun fetchEpisodes(id: String): Flow<Result<List<Episode>>> {
        return flow {
            emit(Result.success(api.fetchEpisodes(id)))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}

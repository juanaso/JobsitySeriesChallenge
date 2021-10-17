package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class SeriesListService(
    private var api : SeriesAPI
) {

    suspend fun fetchSeriesList(): Flow<Result<List<Series>>> {
        return flow{
            emit(Result.success(api.fetchAllSeriesList()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}

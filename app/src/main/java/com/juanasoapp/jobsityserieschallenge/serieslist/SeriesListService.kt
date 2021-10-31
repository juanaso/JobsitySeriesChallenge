package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class SeriesListService @Inject constructor(
    private var api: SeriesAPI,
    private var mapper: SearchedSeriesMapper
) {
    private val errorMessage = "Something went wrong"

    suspend fun fetchSeriesList(): Flow<Result<List<Series>>> {
        return flow {
            Thread.sleep(500) //added to properly test the spinner
            emit(Result.success(api.fetchAllSeriesList()))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }

    fun fetchQuerySeriesList(testString: String): Flow<Result<List<Series>>> {
        return flow {
            Thread.sleep(500) //added to properly test the spinner
            emit(Result.success(mapper(api.fetchWithQuerySeriesList(testString))))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}

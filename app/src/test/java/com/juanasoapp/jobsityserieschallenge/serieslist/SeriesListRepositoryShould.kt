package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.serieslist.api.SeriesListRepository
import com.juanasoapp.jobsityserieschallenge.serieslist.api.SeriesListService
import com.juanasoapp.jobsityserieschallenge.serieslist.model.Series
import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SeriesListRepositoryShould : BaseUnitTest() {


    private val service: SeriesListService = mock()
    private val seriesList = mock<List<Series>>()
    private val exception = RuntimeException(errorMessage)
    private val testString = "arrow"

    @Test
    fun getSeriesListFromService() = runBlockingTest {
        val repository =  SeriesListRepository(service)
        repository.getSeriesList()
        verify(service, times(1)).fetchSeriesList()
    }

    @Test
    fun getSerchedSeriesListFromService() = runBlockingTest {
        val repository = SeriesListRepository(service)
        repository.getSeriesList(testString)
        verify(service, times(1)).fetchQuerySeriesList(testString)
    }

    @Test
    fun emitsSeriesListFromService()=runBlockingTest{
        val repository = mockSuccessfulCase()
        assertEquals(seriesList,repository.getSeriesList().first().getOrNull())

    }

    @Test
    fun propagateErrors() = runBlockingTest{
        val repository = mockFailureCase()

        assertEquals(exception,repository.getSeriesList().first().exceptionOrNull())
    }



    private fun mockFailureCase(): SeriesListRepository {
        runBlocking {
            whenever(service.fetchSeriesList()).thenReturn(
                flow { emit(Result.failure<List<Series>>(exception)) }
            )
        }
        return SeriesListRepository(service)
    }


    private fun mockSuccessfulCase(): SeriesListRepository {
        runBlocking {
            whenever(service.fetchSeriesList()).thenReturn(
                flow { emit(Result.success(seriesList)) }
            )
        }

        return SeriesListRepository(service)
    }
}
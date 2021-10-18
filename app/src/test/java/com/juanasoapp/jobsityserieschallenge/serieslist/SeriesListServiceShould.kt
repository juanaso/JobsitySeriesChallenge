package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

class SeriesListServiceShould : BaseUnitTest() {
    private var api: SeriesAPI = mock()
    private val mapper: SearchedSeriesMapper = mock()
    private val seriesList: List<Series> = mock()
    private val searchedSeriesList: SeriesSearchResponse = mock()
    private lateinit var service: SeriesListService
    private val testString = "arrow"

    @Test
    fun fetchSeriesListFromSeriesAPI() = runBlockingTest {
        service = SeriesListService(api, mapper)
        service.fetchSeriesList().first()
        verify(api, times(1)).fetchAllSeriesList()
    }

    @Test
    fun fetchSearchedSeriesListFromSeriesAPI() = runBlockingTest {
        service = SeriesListService(api, mapper)
        service.fetchQuerySeriesList(testString).first()
        verify(api, times(1)).fetchWithQuerySeriesList(testString)
    }

    @Test
    fun convertValuestoFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()

        assertEquals(Result.success(seriesList), service.fetchSeriesList().first())
    }

    @Test
    fun convertSearchedValuestoFlowResultAndEmitsThem() = runBlockingTest {
        runBlocking {
            whenever(api.fetchWithQuerySeriesList(testString)).thenReturn(searchedSeriesList)
            whenever(mapper.invoke(searchedSeriesList)).thenReturn(seriesList)
            service = SeriesListService(api, mapper)
        }

        assertEquals(
            Result.success(seriesList),
            service.fetchQuerySeriesList(testString).first()
        )
    }

    @Test
    fun emitsErrorResultWhenNetworksFails() = runBlockingTest {
        mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchSeriesList().first().exceptionOrNull()?.message
        )
    }

    @Test
    fun delegateCastingToMapper() = runBlockingTest {
        runBlocking {
            whenever(api.fetchWithQuerySeriesList(testString)).thenReturn(searchedSeriesList)
            whenever(mapper.invoke(searchedSeriesList)).thenReturn(seriesList)
            service = SeriesListService(api, mapper)
        }
        service.fetchQuerySeriesList(testString).first()
        verify(mapper, times(1)).invoke(searchedSeriesList)
    }


    private suspend fun mockFailureCase() {
        whenever(api.fetchAllSeriesList()).thenThrow(RuntimeException("Backend Exception"))
        service = SeriesListService(api, mapper)
    }

    private suspend fun mockSuccessCase() {
        whenever(api.fetchAllSeriesList()).thenReturn(seriesList)
        service = SeriesListService(api, mapper)
    }
}
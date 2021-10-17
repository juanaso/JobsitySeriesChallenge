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

class SeriesListServiceShould:BaseUnitTest() {
    private var api :SeriesAPI = mock()
    private val seriesList : List<Series> = mock()
    private lateinit var service : SeriesListService

    @Test
    fun fetchSeriesListFromSeriesAPI()  = runBlockingTest {
        service = SeriesListService(api)
        service.fetchSeriesList().first()
        verify(api, times(1)).fetchAllSeriesList()
    }

    @Test
    fun convertValuestoFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()

        assertEquals(Result.success(seriesList),service.fetchSeriesList().first())
    }

    @Test
    fun emitsErrorResultWhenNetworksFails()= runBlockingTest {
        mockFailureCase()

        assertEquals("Something went wrong",service.fetchSeriesList().first().exceptionOrNull()?.message)
    }

    private fun mockFailureCase() {
        whenever(api.fetchAllSeriesList()).thenThrow(RuntimeException("Backend Exception"))
        service = SeriesListService(api)
    }

    private fun mockSuccessCase() {
        whenever(api.fetchAllSeriesList()).thenReturn(seriesList)
        service = SeriesListService(api)
    }
}
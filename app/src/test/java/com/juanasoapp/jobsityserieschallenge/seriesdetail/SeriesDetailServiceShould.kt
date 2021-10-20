package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class SeriesDetailServiceShould:BaseUnitTest() {

    private val id = "1"
    private var api: SeriesAPI = mock()
    private val seriesList: List<Episode> = mock()


    @Test
    fun fetchEpisodesFromAPI() = runBlockingTest {
        val service = SeriesDetailService(api)
        service.fetchEpisodes(id).first()
        verify(api, times(1)).fetchEpisodes(id)
    }

    @Test
    fun emitsResultFromAPI() = runBlockingTest {
        val service = mockSuccessfulCase()

        Assert.assertEquals(Result.success(seriesList), service.fetchEpisodes(id).first())
    }

    @Test
    fun emitsErrorResultWhenNetworksFails()= runBlockingTest {
        val service = mockFailureCase()
        Assert.assertEquals(
            errorMessage,
            service.fetchEpisodes(id).first().exceptionOrNull()?.message
        )
    }

    private fun mockFailureCase(): SeriesDetailService {
        runBlocking{
            whenever(api.fetchEpisodes(id)).thenThrow(RuntimeException(backendExceptionErrorMessage))
        }
        return SeriesDetailService(api)
    }

    private fun mockSuccessfulCase(): SeriesDetailService {
        runBlocking{
            whenever(api.fetchEpisodes(id)).thenReturn(seriesList)
        }
        return SeriesDetailService(api)
    }
}
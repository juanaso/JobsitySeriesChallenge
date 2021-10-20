package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class SeriesDetailRepositoryShould:BaseUnitTest() {

    private val service: SeriesDetailService = mock()
    private val episodes = mock<List<Episode>>()
    private val exception = RuntimeException(errorMessage)
    private val id = "1"


    @Test
    fun getEpisodesFromService() = runBlockingTest {
        val repository = SeriesDetailRepository(service)
        repository.getEpisodes(id)
        verify(service, times(1)).fetchEpisodes(id)
    }
    @Test
    fun emitsEpisodesFromService() = runBlockingTest{
        val repository = mockSuccessfulCase()
        Assert.assertEquals(episodes, repository.getEpisodes(id).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest{
        val repository =  mockFailureCase()

        Assert.assertEquals(exception, repository.getEpisodes(id).first().exceptionOrNull())
    }

    private fun mockSuccessfulCase(): SeriesDetailRepository {
        whenever(service.fetchEpisodes(id)).thenReturn(
            flow { emit(Result.success(episodes)) }
        )

        return SeriesDetailRepository(service)
    }

    private fun mockFailureCase(): SeriesDetailRepository {
        runBlocking {
            whenever(service.fetchEpisodes(id)).thenReturn(
                flow { emit(Result.failure<List<Episode>>(exception)) }
            )
        }
        return SeriesDetailRepository(service)
    }
}
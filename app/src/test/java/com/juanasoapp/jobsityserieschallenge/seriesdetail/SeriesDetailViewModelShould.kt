package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.seriesdetail.api.SeriesDetailRepository
import com.juanasoapp.jobsityserieschallenge.seriesdetail.model.Episode
import com.juanasoapp.jobsityserieschallenge.seriesdetail.viewmodel.SeriesDetailViewModel
import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.juanasoapp.jobsityserieschallenge.utils.captureValues
import com.juanasoapp.jobsityserieschallenge.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class SeriesDetailViewModelShould : BaseUnitTest() {
    private val repository: SeriesDetailRepository = mock()
    private val episodes = mock<List<Episode>>()
    private val expected = Result.success(episodes)
    private val exception = RuntimeException(errorMessage)
    private val id = "1"

    @Test
    fun getEpisodeListFromRepository() = runBlockingTest {
        var viewModel = mockSuccessfulCase()
        viewModel.getEpisodes(id)
        viewModel.episodes.getValueForTest()
        verify(repository, times(1)).getEpisodes(id)
    }

    @Test
    fun emitsEpisodesListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getEpisodes(id)
        assertEquals(expected, viewModel.episodes.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.getEpisodes(id)
        assertEquals(exception, viewModel.episodes.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun displaySpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getEpisodes(id)
            viewModel.episodes.getValueForTest()
            TestCase.assertEquals(true, values[0])
        }
    }

    @Test
    fun closeSpinnerAfterSeriesListLoads() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getEpisodes(id)
            viewModel.episodes.getValueForTest()
            TestCase.assertEquals(false, values.last())
        }
    }

    private fun mockFailureCase(): SeriesDetailViewModel {
        runBlocking {
            whenever(repository.getEpisodes(id)).thenReturn(
                flow { emit(Result.failure<List<Episode>>(exception)) }
            )
        }

        return SeriesDetailViewModel(repository)
    }


    private fun mockSuccessfulCase(): SeriesDetailViewModel {
        runBlocking {
            whenever(repository.getEpisodes(id)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return SeriesDetailViewModel(repository)
    }
}
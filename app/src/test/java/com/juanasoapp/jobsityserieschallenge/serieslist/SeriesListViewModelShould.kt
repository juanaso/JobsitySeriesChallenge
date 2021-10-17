package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import com.juanasoapp.jobsityserieschallenge.serieslist.SeriesListRepository
import com.juanasoapp.jobsityserieschallenge.serieslist.SeriesListViewModel
import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.juanasoapp.jobsityserieschallenge.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SeriesListViewModelShould : BaseUnitTest() {

    private val repository: SeriesListRepository = mock()
    private val seriesList = mock<List<Series>>()
    private val expected = Result.success(seriesList)
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getSeriesListFromRepository() = runBlockingTest {
        var viewModel = mockSuccessfulCase()
        viewModel.seriesList.getValueForTest()

        verify(repository, times(1)).getSeriesList()
    }

    @Test
    fun emitsSeriesListFromRepository() = runBlockingTest {
        var viewModel = mockSuccessfulCase()
        assertEquals(expected, viewModel.seriesList.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        runBlocking {
            whenever(repository.getSeriesList()).thenReturn(
                flow { emit(Result.failure<List<Series>>(exception)) }
            )
        }

        val viewModel = SeriesListViewModel(repository)
        assertEquals(exception, viewModel.seriesList.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockSuccessfulCase(): SeriesListViewModel {
        runBlocking {
            whenever(repository.getSeriesList()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        var viewModel =
            SeriesListViewModel(
                repository
            )
        return viewModel
    }
}
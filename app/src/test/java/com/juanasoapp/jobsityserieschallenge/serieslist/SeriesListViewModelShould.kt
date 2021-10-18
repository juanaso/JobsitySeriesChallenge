package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.utils.BaseUnitTest
import com.juanasoapp.jobsityserieschallenge.utils.captureValues
import com.juanasoapp.jobsityserieschallenge.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SeriesListViewModelShould : BaseUnitTest() {

    private val testString = "under"
    private val repository: SeriesListRepository = mock()
    private val seriesList = mock<List<Series>>()
    private val expected = Result.success(seriesList)
    private val seriesListWithSeriesMocked = listOf(mock<Series>(), mock<Series>())
    private val expectedWithTwoItems = Result.success(seriesListWithSeriesMocked)
    private val exception = RuntimeException("Something went wrong")

    private val searchedSeriesList = mock<List<Series>>()
    private val searchedExpected = Result.success(searchedSeriesList)


    @ExperimentalCoroutinesApi
    @Test
    fun getSeriesListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.searchChanel.send("")
        viewModel.searchChanel.openSubscription().cancel()
        viewModel.seriesList.getValueForTest()
        verify(repository, times(1)).getSeriesList()
    }

//    @Test
//    fun myTest() = runBlockingTest {
//        val myChannel = ConflatedBroadcastChannel<String>()
//        myChannel.send("hello")
//        val subscription = myChannel.openSubscription()
//        val result = subscription.receive()
//        subscription.cancel()
//        assertEquals("hello", result)
//    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsSeriesListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.searchChanel.send("")
        viewModel.searchChanel.openSubscription().cancel()
        assertEquals(expected, viewModel.seriesList.getValueForTest())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError()=runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.searchChanel.send("")
        viewModel.searchChanel.openSubscription().cancel()
        assertEquals(exception, viewModel.seriesList.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockFailureCase(): SeriesListViewModel {
        runBlocking {
            whenever(repository.getSeriesList()).thenReturn(
                flow { emit(Result.failure<List<Series>>(exception)) }
            )
        }

        return SeriesListViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun displaySpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.seriesList.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun closeSpinnerAfterSeriesListLoads() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.searchChanel.send("")
            viewModel.searchChanel.openSubscription().cancel()
            viewModel.seriesList.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emptySeriesListOnSetQueryTest() = runBlockingTest {
        val viewModel = mockSuccessfulWithTwoItemCase()
        viewModel.searchChanel.send("")
        viewModel.searchChanel.openSubscription().cancel()
        viewModel.onTextSet(testString)
        assertEquals(0, viewModel.seriesList.value?.getOrNull()!!.size)
    }


    private fun mockSuccessfulWithTwoItemCase(): SeriesListViewModel {
        runBlocking {
            whenever(repository.getSeriesList()).thenReturn(
                flow {
                    emit(expectedWithTwoItems)
                }
            )
        }
        return SeriesListViewModel(repository)
    }

    private fun mockSuccessfulCase(): SeriesListViewModel {
        runBlocking {
            whenever(repository.getSeriesList()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return SeriesListViewModel(repository)
    }
}
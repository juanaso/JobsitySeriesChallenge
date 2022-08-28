package com.juanasoapp.jobsityserieschallenge.serieslist.viewmodel

import androidx.lifecycle.*
import com.juanasoapp.jobsityserieschallenge.serieslist.api.SeriesListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class SeriesListViewModel(
    private val repository: SeriesListRepository
) : ViewModel() {
    var loader = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    @ExperimentalCoroutinesApi
    val searchChanel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val seriesList = liveData {
        isLoading.postValue(true)
        emitSource(searchChanel.asFlow()
            .flatMapConcat {
                repository.getSeriesList(it)
            }
            .onEach {
                isLoading.postValue(false)
            }
            .asLiveData())
    }

    @ExperimentalCoroutinesApi
    fun onTextSet(query: String) {
        isLoading.postValue(true)
        (seriesList as MutableLiveData).value = Result.success(emptyList())
        searchChanel.offer(query)
    }
}
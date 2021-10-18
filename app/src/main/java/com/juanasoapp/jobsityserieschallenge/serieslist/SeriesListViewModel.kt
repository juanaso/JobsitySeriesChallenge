package com.juanasoapp.jobsityserieschallenge.serieslist

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class SeriesListViewModel(
    private val repository: SeriesListRepository
): ViewModel() {
    var loader = MutableLiveData<Boolean>()

    @ExperimentalCoroutinesApi
     val searchChanel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val seriesList = liveData {
        loader.postValue(true)
        emitSource(searchChanel.asFlow()
            .flatMapConcat {
                repository.getSeriesList(it)}
            .onEach { loader.postValue(false) }
            .asLiveData())
    }

//    @ExperimentalCoroutinesApi
//    val seriesList = liveData {
//        loader.postValue(true)
//        emitSource(searchChanel.asFlow()
//            .onEach { loader.postValue(false) }
//            .flatMapLatest { search ->
//                repository.getSeriesList(search)
//            }
//            .catch { throwable ->
////            _snackbar.value = throwable.message
//            }.asLiveData())
//    }


//.launchIn(CoroutineScope(Dispatchers.IO))

    @ExperimentalCoroutinesApi
    fun onTextSet(query: String) {
        (seriesList as MutableLiveData).value = Result.success(emptyList())
        searchChanel.offer(query)
    }
}
package com.juanasoapp.jobsityserieschallenge.seriesdetail

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SeriesDetailViewModel(
    private var repository: SeriesDetailRepository
) : ViewModel() {

    var loader = MutableLiveData<Boolean>()
    val episodes = MutableLiveData<Result<List<Episode>>>()

    fun getEpisodes(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            repository.getEpisodes(id)
                .onEach { loader.postValue(false) }
                .collect {
                    episodes.postValue(it)
                }
        }
    }
}

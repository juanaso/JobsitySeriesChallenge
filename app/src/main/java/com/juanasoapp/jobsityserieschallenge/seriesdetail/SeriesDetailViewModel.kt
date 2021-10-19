package com.juanasoapp.jobsityserieschallenge.seriesdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.juanasoapp.jobsityserieschallenge.seriesdetail.SeriesDetailRepository
import kotlinx.coroutines.flow.onEach

class SeriesDetailViewModel (
    private var repository: SeriesDetailRepository
):ViewModel() {

    var loader = MutableLiveData<Boolean>()

    val episodes = liveData {
        loader.postValue(true)
        emitSource(repository.getEpisodes()
            .onEach { loader.postValue(false) }
            .asLiveData())
    }
}

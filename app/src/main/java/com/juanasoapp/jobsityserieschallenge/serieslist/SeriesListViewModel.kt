package com.juanasoapp.jobsityserieschallenge.serieslist

import androidx.lifecycle.*

class SeriesListViewModel(
    private val repository: SeriesListRepository
): ViewModel() {

    val seriesList = liveData {
        emitSource(repository.getSeriesList().asLiveData())
    }

}

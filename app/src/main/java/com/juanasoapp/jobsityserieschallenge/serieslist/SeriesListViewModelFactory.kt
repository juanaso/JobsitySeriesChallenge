package com.juanasoapp.jobsityserieschallenge.serieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SeriesListViewModelFactory @Inject constructor(private val repository: SeriesListRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SeriesListViewModel(
            repository
        ) as T
    }
}

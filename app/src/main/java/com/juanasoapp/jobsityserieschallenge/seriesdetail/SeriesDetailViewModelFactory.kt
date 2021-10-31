package com.juanasoapp.jobsityserieschallenge.seriesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SeriesDetailViewModelFactory @Inject constructor(private val repository: SeriesDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SeriesDetailViewModel(
            repository
        ) as T
    }
}

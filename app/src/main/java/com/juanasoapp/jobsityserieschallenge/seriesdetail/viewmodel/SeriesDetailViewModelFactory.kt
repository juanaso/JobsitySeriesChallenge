package com.juanasoapp.jobsityserieschallenge.seriesdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanasoapp.jobsityserieschallenge.seriesdetail.api.SeriesDetailRepository
import javax.inject.Inject

class SeriesDetailViewModelFactory @Inject constructor(private val repository: SeriesDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SeriesDetailViewModel(
            repository
        ) as T
    }
}

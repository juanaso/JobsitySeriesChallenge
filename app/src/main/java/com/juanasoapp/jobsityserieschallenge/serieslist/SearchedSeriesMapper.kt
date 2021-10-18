package com.juanasoapp.jobsityserieschallenge.serieslist

import javax.inject.Inject

class SearchedSeriesMapper @Inject constructor() :Function1<SeriesSearchResponse, List<Series>>{

    override fun invoke(searchedSeries: SeriesSearchResponse): List<Series> {
        return searchedSeries.map { it.show }
    }

}

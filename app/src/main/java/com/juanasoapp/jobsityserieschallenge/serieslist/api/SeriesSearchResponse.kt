package com.juanasoapp.jobsityserieschallenge.serieslist

import com.juanasoapp.jobsityserieschallenge.serieslist.model.Series

class SeriesSearchResponse() : ArrayList<SeriesSearchRaw>()

class SeriesSearchRaw(
    var show: Series
)
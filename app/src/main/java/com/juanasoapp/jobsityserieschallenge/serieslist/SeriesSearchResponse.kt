package com.juanasoapp.jobsityserieschallenge.serieslist

class SeriesSearchResponse():ArrayList<SeriesSearchRaw>(){

}
class SeriesSearchRaw(
    var score:String,
    var show:Series
) {
}
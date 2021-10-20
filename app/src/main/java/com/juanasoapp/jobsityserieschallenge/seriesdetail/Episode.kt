package com.juanasoapp.jobsityserieschallenge.seriesdetail

import com.juanasoapp.jobsityserieschallenge.serieslist.Images

class Episode(
    val id:String,
    val number:String,
    val name:String?="",
    val summary:String,
    val season:String,
    val image: Images?) {
}

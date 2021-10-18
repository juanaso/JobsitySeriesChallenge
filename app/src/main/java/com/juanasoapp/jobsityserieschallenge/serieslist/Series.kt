package com.juanasoapp.jobsityserieschallenge.serieslist

class Series(
    val id:String,
    val image: Images,
    val name:String,
    val schedule: Schedule,
    val genres:ArrayList<String>,
    val summary:String
    ) {}


class Schedule(
    val time:String,
    val days:ArrayList<String>
    )

class Images(
    val medium:String,
    val original:String,
    )

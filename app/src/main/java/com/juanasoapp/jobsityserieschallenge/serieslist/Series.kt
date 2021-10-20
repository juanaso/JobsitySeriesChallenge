package com.juanasoapp.jobsityserieschallenge.serieslist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Series(
    val id:String,
    val image: Images?,
    val name:String?,
    val schedule: Schedule?,
    val genres:ArrayList<String>?,
    val summary:String?
    ): Parcelable {}

@Parcelize
class Schedule(
    val time:String?,
    val days:ArrayList<String>?
    ):Parcelable{}

@Parcelize
class Images(
    val medium:String?,
    val original:String?,
    ):Parcelable

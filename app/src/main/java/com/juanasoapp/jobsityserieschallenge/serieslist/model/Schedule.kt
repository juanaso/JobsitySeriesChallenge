package com.juanasoapp.jobsityserieschallenge.serieslist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Schedule(
    val time: String?,
    val days: ArrayList<String>?
) : Parcelable

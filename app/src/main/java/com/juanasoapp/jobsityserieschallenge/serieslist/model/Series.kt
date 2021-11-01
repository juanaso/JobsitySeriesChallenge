package com.juanasoapp.jobsityserieschallenge.serieslist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Series(
    val id: String,
    val image: Images?,
    val name: String?,
    val schedule: Schedule?,
    val genres: ArrayList<String>?,
    val summary: String?
) : Parcelable

package com.juanasoapp.jobsityserieschallenge.seriesdetail.model

import android.os.Parcelable
import com.juanasoapp.jobsityserieschallenge.serieslist.model.Images
import kotlinx.android.parcel.Parcelize

@Parcelize
class Episode(
    val id: String,
    val number: String,
    val name: String? = "",
    val summary: String,
    val season: String,
    val image: Images?
) : Parcelable

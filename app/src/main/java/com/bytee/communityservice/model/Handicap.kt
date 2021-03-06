package com.bytee.communityservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Handicap(
    val ngoName: String,
    val managerName: String?,
    val email: String,
    val address: String,
    val lat: String,
    val long: String,
    val time: String,
    val description: String
): Parcelable
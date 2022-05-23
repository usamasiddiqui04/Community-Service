package com.bytee.communityservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Environmental(
    val driveName : String,
    val address : String,
    val time : String,
    val latitude : String,
    val longitude : String,
    val description : String
): Parcelable

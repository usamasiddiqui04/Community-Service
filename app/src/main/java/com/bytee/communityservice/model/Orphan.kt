package com.bytee.communityservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Orphan(
    val managerName : String,
    val orphanName : String,
    val email : String,
    val address : String,
    val date : String,
    val description : String
): Parcelable
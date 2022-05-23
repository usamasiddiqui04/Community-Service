package com.bytee.communityservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BloodDonor(
    val managerName : String,
    val email : String,
    val hospitalName : String,
    val bloodGroup : String,
    val patientName : String,
    val latitude : String,
    val longitude : String,
    val description : String
) : Parcelable

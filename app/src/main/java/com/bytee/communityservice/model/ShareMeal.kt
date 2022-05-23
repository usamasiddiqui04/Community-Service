package com.bytee.communityservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShareMeal(

    val managerName : String,
    val restaurantName : String,
    val email : String,
    val address : String,
    val latitude : String,
    val longitude : String,
    val description : String,
    val time :String
): Parcelable

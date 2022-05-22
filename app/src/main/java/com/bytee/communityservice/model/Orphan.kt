package com.bytee.communityservice.model

data class Orphan(
    val managerName : String,
    val orphanName : String,
    val email : String,
    val address : String,
    val date : String,
    val description : String
)
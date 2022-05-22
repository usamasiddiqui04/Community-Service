package com.bytee.communityservice.utils


import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Type

class Prefs (context: Context) {

    private val appPreferences: SharedPreferences = context.getSharedPreferences(AppConfigurationKey, Context.MODE_PRIVATE)

    var type: String?
        get() = appPreferences.getString(UserType, "")
        set(value) = appPreferences.edit().putString(UserType, value).apply()



    var name : String?
        get() = appPreferences.getString(UserName, "")
        set(value) = appPreferences.edit().putString(UserName, value).apply()

    var email : String?
        get() = appPreferences.getString(UserEmail, "")
        set(value) = appPreferences.edit().putString(UserEmail, value).apply()

    var phoneNumber : String?
        get() = appPreferences.getString(UserPhone, "")
        set(value) = appPreferences.edit().putString(UserPhone, value).apply()








    fun clearPreferences(){
        appPreferences.edit().clear().apply()
    }

    companion object{

        private const val AppConfigurationKey = "ai.stech.staticfrs.configurations"
        private const val UserName = "userName"
        private const val UserType = "userType"
        private const val UserEmail = "userEmail"
        private const val UserPhone = "userPhone"

    }
}

package com.example.malleaume.myapplication.data.model

import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences



class Session() {

    private var prefs: SharedPreferences? = null
    fun Session(cntx: Context){

        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }

    fun setusename(username: String) {
        prefs!!.edit().putString("usename", username).apply()
    }
    fun setMail(mail: String){
        prefs!!.edit().putString("mail", mail).apply()
    }

    fun getusename(): String? {
        return prefs!!.getString("usename", "")
    }

    fun getumail(): String? {
        return prefs!!.getString("mail", "")
    }
}
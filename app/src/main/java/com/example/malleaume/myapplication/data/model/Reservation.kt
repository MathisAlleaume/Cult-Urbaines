package com.example.malleaume.myapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Reservation(var IDFEST:Int, var IDEVENT:Int, var JJMMAAA: java.util.Date?):
    Parcelable
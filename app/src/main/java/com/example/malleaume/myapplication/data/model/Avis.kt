package com.example.malleaume.myapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.util.*


@Parcelize
data class Avis(  var TITRE :String?,var NOTE : Float?, var DESCRIPTIFAVIS : String?,var EstAccepte: Int?,var EstAdmin: Int?, var DATEAVIS : java.util.Date?,var IDEVENT: Int?, var IDFEST: Int?):
    Parcelable
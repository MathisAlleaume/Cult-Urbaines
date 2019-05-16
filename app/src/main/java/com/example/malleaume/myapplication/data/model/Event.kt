package com.example.malleaume.myapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.InputStream
import java.util.*

@Parcelize
data class Event(var IDEVENT: Int?, var IDLIEU :Int, var IDTYPEEVENT: Int, var LIBELLE: String, var DATEEVENT: String, var DESCRIPTIF : String, var picture : String?, var LIBELLELIEU: String?, var LIBELLETYPEEVENT: String?):
    Parcelable
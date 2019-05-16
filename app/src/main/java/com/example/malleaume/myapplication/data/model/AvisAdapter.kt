package com.example.malleaume.myapplication.data.model

import android.widget.TextView
import android.widget.ArrayAdapter


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import android.view.*
import android.widget.ImageView
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Event

import android.os.Bundle
import android.widget.RatingBar
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity
import com.example.malleaume.myapplication.Ui.EventDetails
import kotlinx.android.synthetic.main.item_event.view.*


class AvisAdapter(context: Context, Avis: ArrayList<Avis>) : ArrayAdapter<Avis>(context, 0, Avis) {

@RequiresApi(Build.VERSION_CODES.O)
override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var convertView = convertView

    // Get the data item for this position
    val avis = getItem(position)
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_avis, parent, false)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    // Lookup view for data population
    val TvTitreAvis = convertView?.findViewById(R.id.tvTitreAvis) as TextView?
    val notebar = convertView?.findViewById(R.id.ratingBar2) as RatingBar?
    val tvDescAvis = convertView!!.findViewById(R.id.tvDescAvis) as TextView?



    // Populate the data into the template view using the data object
    TvTitreAvis!!.text = avis!!.TITRE
    notebar!!.setRating(avis.NOTE!!)
    tvDescAvis!!.text = avis!!.DESCRIPTIFAVIS



    // Return the completed view to render on screen
    return convertView


}



}




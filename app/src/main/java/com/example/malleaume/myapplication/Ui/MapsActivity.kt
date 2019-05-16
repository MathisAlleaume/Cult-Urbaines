package com.example.malleaume.myapplication.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Event

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity() : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val EVENT = "EVENT"
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps2)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val bundle: Bundle? = intent.extras
        val event: Event = bundle!!.get(EventDetails.EVENT) as Event


        val tvTitre = findViewById<TextView>(R.id.TvTitleBar)

        tvTitre.text = event.LIBELLE
    }

    override fun onMapReady(map: GoogleMap) {
        val bundle: Bundle? = intent.extras
        val event: Event = bundle!!.get(EventDetails.EVENT) as Event
        val lieu = LatLng(48.668359, -3.91428)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lieu, 12.0f))
        map.addMarker(
            MarkerOptions()
                .position(lieu)
                .title(event.LIBELLE)
        )
    }
}

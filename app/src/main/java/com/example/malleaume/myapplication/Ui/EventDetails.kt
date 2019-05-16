package com.example.malleaume.myapplication.Ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Avis
import com.example.malleaume.myapplication.data.model.Event
import com.example.malleaume.myapplication.data.model.Reservation
import com.google.android.gms.maps.*


import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.dialog_avis.*
import kotlinx.android.synthetic.main.item_event.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class EventDetails : AppCompatActivity(), OnMapReadyCallback {


    companion object {
        const val EVENT = "EVENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val idfest = SharedPreferences.getInt("ID", 0)
        val apiService = ApiService.Builder.instance

        val bundle: Bundle? = intent.extras
        val event: Event = bundle!!.get(EVENT) as Event

        val TvTitreEvent = findViewById<TextView>(R.id.TvTitre)
        val TvDesc = findViewById<TextView>(R.id.TvDesc)
        val TvLieu = findViewById<TextView>(R.id.TvAdresse)
        val TvDateDetails = findViewById<TextView>(R.id.TvDate)
        val openFullMapBtn = findViewById<FloatingActionButton>(R.id.BtnMap)
        val BtnRes = findViewById<Button>(R.id.btnRes)
        val BtnAvis = findViewById<Button>(R.id.BtnAvis)


        openFullMapBtn.setOnClickListener { openFullMap() }

        if (SharedPreferences.getInt("ID", 0) == 0) {
            BtnRes.text = "Veuillez vous conecter"
            BtnRes.setOnClickListener() {
                onResWithoutCon()
            }
            Log.d("pas de mail", "pas de reservation possible")
        } else {
            checkRes(idfest, event.IDEVENT!!, apiService)
        }


        TvTitreEvent.text = event.LIBELLE
        TvDesc.text = event.DESCRIPTIF
        TvDateDetails.text = event.DATEEVENT

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        BtnAvis.setOnClickListener{



            val intent = Intent(this@EventDetails, consultation_avis()::class.java)
            intent.putExtra(EVENT, event)
            ContextCompat.startActivity(this@EventDetails, intent, Bundle())
        }
    }


    private fun openFullMap() {
        val bundle: Bundle? = intent.extras
        val event: Event = bundle!!.get(EVENT) as Event

        val intent = Intent(this@EventDetails, MapsActivity()::class.java)
        intent.putExtra(MapsActivity.EVENT, event)
        ContextCompat.startActivity(this@EventDetails, intent, Bundle())
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

    fun onResWithoutCon() {
        Toast.makeText(
            this@EventDetails, "Vous devez être connecté pour pouvoir reserver cet évènement",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this, Connexion::class.java)
        startActivity(intent)
    }

    private fun onRes(idfest: Int, idevent: Int, apiService: ApiService) {
        val sqlDate = Date()
        val res = Reservation(idfest, idevent, sqlDate)


        apiService.reserver(res).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("réservé", sqlDate.toString())
                Toast.makeText(
                    this@EventDetails, "Reservation éffectuée",
                    Toast.LENGTH_LONG
                ).show()
                btnRes.text = "Réservé"
                btnRes.isClickable = false
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("erreur : ", t.toString())
            }
        })
    }

    fun checkRes(idfest: Int, idevent: Int, apiService: ApiService) {
        val res = Reservation(idfest, idevent, null)
        apiService.checkRes(res).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("ok", response.body())
                val BtnRes = findViewById<Button>(R.id.btnRes)
                val bundle: Bundle? = intent.extras
                val event: Event = bundle!!.get(EVENT) as Event
                if (response.body() == "true") {



                    BtnAvis.setOnClickListener {
                        Toast.makeText(
                            this@EventDetails, "Vous n'avez pas réservé cet event",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    BtnRes.setOnClickListener {
                        BtnRes.text = "Reserver"
                        MaterialDialog(this@EventDetails).show {
                            title(R.string.titreRes)
                            positiveButton(R.string.submit) { dialog ->
                                onRes(idfest, event.IDEVENT!!, apiService)
                            }
                            negativeButton(R.string.cancel)
                        }
                    }

                } else {
                    btnRes.text = "Réservé"

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {


            }
        })
    }

}



package com.example.malleaume.myapplication.Ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import kotlinx.android.synthetic.main.activity_accueil.*
import kotlinx.android.synthetic.main.activity_inscription.*
import retrofit2.Call
import retrofit2.Response


class accueil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val apiService = ApiService.Builder.instance

        if (SharedPreferences.getInt("ID", 0) == 0){
            BtnCon.setOnClickListener { connexion() }
            BtnIns.setOnClickListener { inscription() }
            BtnCont.setOnClickListener{ consultationEventOffline()}
            BtnCon.text = "Connexion"
            BtnIns.text = "Inscription"
            BtnCont.visibility = View.VISIBLE
        }else{
            BtnCon.setOnClickListener { mesRes() }
            BtnIns.setOnClickListener { deconnexion() }
            BtnCon.text = "Mes Reservations"
            BtnIns.text = "Deconnexion"
            BtnCont.text = "Consulter les évènements"
            BtnCont.setOnClickListener{ consultationEventOffline()}
            val id = SharedPreferences.getInt("ID", 0)
            apiService.getById(id).enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                   TvPrenom.text = response.body().toString()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("erreur :", t.toString())
                }
            })

        }


    }
    fun connexion(){
        val intent = Intent(this, Connexion::class.java)
        startActivity(intent)
    }
    fun inscription(){
        val intent = Intent(this, inscription::class.java)
        startActivity(intent)
    }
    fun consultationEventOffline(){
        val intent = Intent(this, ConsultationEventOffline()::class.java)
        startActivity(intent)
    }

    fun mesRes(){
        val intent = Intent(this, MesReservations()::class.java)
        startActivity(intent)
    }
    fun deconnexion() {
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val editor = SharedPreferences.edit()
        editor.clear()
        editor.apply()
        finish()
        val intent = Intent(this, accueil()::class.java)
        startActivity(intent)
    }


    fun getName(){

    }
}

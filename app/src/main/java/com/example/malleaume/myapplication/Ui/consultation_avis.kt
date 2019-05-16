package com.example.malleaume.myapplication.Ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Avis
import com.example.malleaume.myapplication.data.model.AvisAdapter
import com.example.malleaume.myapplication.data.model.Event
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.dialog_avis.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class consultation_avis : AppCompatActivity() {
    companion object {
        const val EVENT = "EVENT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_avis)

        val bundle: Bundle? = intent.extras
        val event: Event = bundle!!.get(EventDetails.EVENT) as Event
        appelAvis(event)
        val apiService = ApiService.Builder.instance
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val idfest = SharedPreferences.getInt("ID", 0)
        BtnAvis.setOnClickListener {
            avis(idfest, event.IDEVENT!!, apiService)
        }
    }

    private fun appelAvis(event: Event) {
        val arrayOfAvis = ArrayList<Avis>()
        val apiService = ApiService.Builder.instance
        apiService.getAvis(event.IDEVENT!!).enqueue(object : Callback<List<Avis>> {
            override fun onResponse(call: Call<List<Avis>>, response: Response<List<Avis>>) {
                val listView = findViewById<ListView>(R.id.RvAvis)
                listView.adapter = null
                val avis = response.body()
                val adapter = AvisAdapter(this@consultation_avis, arrayOfAvis)
                listView.adapter = adapter
                adapter.addAll(avis)
                Log.d("Nif", "bien joué mec")
            }

            override fun onFailure(call: Call<List<Avis>>, t: Throwable) {
                Log.d("Dommage", t.toString())
            }
        })
    }

    fun avis(idfest: Int, idevent: Int, apiService: ApiService) {
        MaterialDialog(this@consultation_avis).show {
            customView(R.layout.dialog_avis)
            title(R.string.titreAvis)
            positiveButton(R.string.submit) { dialog ->
                val avis = Avis(EtTitre.toString(), note.rating, EvAvis.toString(), 0, 0, null, idevent, idfest)
                apiService.createAvis(avis).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(
                            this@consultation_avis, "Avis enregistré",
                            Toast.LENGTH_LONG
                        ).show()
                        val bundle: Bundle? = intent.extras
                        val event: Event = bundle!!.get(EventDetails.EVENT) as Event
                        appelAvis(event)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(
                            this@consultation_avis, "t",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
            negativeButton(R.string.cancel)
        }
    }
}

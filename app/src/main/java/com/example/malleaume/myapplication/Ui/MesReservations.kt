package com.example.malleaume.myapplication.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import EventAdapter
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Event
import kotlinx.android.synthetic.main.activity_consultation_event_offline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MesReservations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mes_reservations)
        val refresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        BtnHome.setOnClickListener { home() }

        refresh.setOnRefreshListener {appelRes()  }
        appelRes()
    }
    private fun home() {
        val intent = Intent(this, accueil::class.java)
        startActivity(intent)
    }

    private fun appelRes() {
        val arrayOfEvent = ArrayList<Event>()
        val apiService = ApiService.Builder.instance
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val idfest = SharedPreferences.getInt("ID", 0)
        apiService.getRes(idfest).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val listView = findViewById<ListView>(R.id.LvEvent)
                listView.adapter = null
                val event = response.body()
                val adapter = EventAdapter(this@MesReservations, arrayOfEvent)



                listView.adapter = adapter

                adapter.addAll(event)
                val refresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
                refresh.isRefreshing = false


                Log.d("Nif", "bien joué mec")

            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.d("Dommage", t.toString())
            }

        })
    }
}

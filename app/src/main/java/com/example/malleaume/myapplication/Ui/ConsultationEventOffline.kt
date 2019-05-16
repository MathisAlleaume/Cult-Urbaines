package com.example.malleaume.myapplication.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.malleaume.myapplication.data.model.Festivalier
import EventAdapter
import android.content.Intent
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Event
import kotlinx.android.synthetic.main.activity_consultation_event_offline.*
import org.json.JSONArray

import java.io.InputStream


//import android.widget.ListView


class ConsultationEventOffline : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_event_offline)
        val refresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        BtnHome.setOnClickListener { home() }

        refresh.setOnRefreshListener {appelEvent()  }
        appelEvent()

        //var itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
    }

    private fun home() {
        val intent = Intent(this, accueil::class.java)
        startActivity(intent)
    }

    private fun appelEvent() {
        val arrayOfEvent = ArrayList<Event>()
        val apiService = ApiService.Builder.instance
        apiService.getEvent().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val listView = findViewById<ListView>(R.id.LvEvent)
                listView.adapter = null
                val event = response.body()
                val adapter = EventAdapter(this@ConsultationEventOffline, arrayOfEvent)



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

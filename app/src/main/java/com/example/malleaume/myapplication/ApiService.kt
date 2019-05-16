package com.example.malleaume.myapplication

import com.example.malleaume.myapplication.data.model.Avis
import com.example.malleaume.myapplication.data.model.Event
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import com.example.malleaume.myapplication.data.model.Festivalier
import com.example.malleaume.myapplication.data.model.Reservation
import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query



/**
 * ApiService
 */
interface ApiService {


    @GET("list.events")
    fun getEvent():Call<List<Event>>

    @POST("inscription")
    fun inscription(@Body fest: Festivalier): Call<Festivalier>

    @POST("connexion")
    fun connexion(@Body fest: Festivalier): Call<Festivalier>

    @POST("get.festbyMail")
    fun getByMail(@Body Mail: String): Call<Int>

    @POST("get.fest")
    fun getById(@Body ID: Int): Call<String>

    @POST("create.res")
    fun reserver(@Body res: Reservation): Call<String>

    @POST("check.res")
    fun checkRes(@Body res: Reservation): Call<String>

    @POST("list.res")
    fun getRes(@Body IDF : Int):Call<List<Event>>

    @POST("create.avis")
    fun createAvis(@Body Avis : Avis):Call<String>

    @POST("get.avis")
    fun getAvis(@Body IDEVENT : Int):Call<List<Avis>>


    object Builder {
        /**
         * Create a singleton only for simplicity. Should be done through a DI system instead.
         */
        val instance = build()

        /**
         * Build an ApiService instance
         */
        private fun build(): ApiService {
            val gson = GsonBuilder().create() // JSON deserializer/serializer

            // Create the OkHttp Instance
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
               // .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl("http://192.168.151.4:8080/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}
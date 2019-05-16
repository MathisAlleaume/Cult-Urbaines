package com.example.malleaume.myapplication.Ui

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.afollestad.vvalidator.form
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Festivalier
import com.example.malleaume.myapplication.data.model.Session
import kotlinx.android.synthetic.main.activity_accueil.*
import kotlinx.android.synthetic.main.activity_inscription.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class inscription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        val EtNom = findViewById<EditText>(R.id.EtNom)
        val EtPre = findViewById<EditText>(R.id.EtPrenom)
        val EtMail = findViewById<EditText>(R.id.EtMail)
        val EtMdp = findViewById<EditText>(R.id.EtMdp)
        val EtConf = findViewById<EditText>(R.id.EtMdpConf)

        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE) //creation de la session



        form {//creation du form
            input(EtNom) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                length().atLeast(3).description("Ce champ doit contenir un minimum de 3 caractères")
            }
            input(EtPrenom) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                length().atLeast(3).description("Ce champ doit contenir un minimum de 3 caractères")
            }
            input(EtMail) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                isEmail().description("Doit correspondre à un email, ex : exemple@ex.fr")
            }
            input(EtMdp) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                length().atLeast(6).description("Ce champ doit contenir un minimum de 6 caractères")
            }
            input(EtConf) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                length().atLeast(6).description("Ce champ doit contenir un minimmum de 6 caractères")
            }
            submitWith(R.id.BtnValIns) { result -> //action a effectuer
                if (EtConf.text.toString() == EtMdp.text.toString()) { //verifier que les mdp soient les mêmes
                    val fest = Festivalier(
                        null,
                        EtNom.text.toString(),
                        EtPre.text.toString(),
                        EtMail.text.toString(),
                        EtMdp.text.toString()
                    )

                    PostInscription(fest) // inscription en bdd u festivalier

                } else {
                    Toast.makeText(
                        this@inscription, "Les mots de passes ne correspondent pas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

    fun PostInscription(fest: Festivalier) {
        val apiService = ApiService.Builder.instance
        apiService.inscription(fest).enqueue(object : Callback<Festivalier> {
            override fun onResponse(call: Call<Festivalier>, response: Response<Festivalier>) {

                Log.d("nif", "test")
                Toast.makeText(
                    this@inscription, "Vous avez été inscrit!",
                    Toast.LENGTH_LONG
                ).show()


                fillSession(apiService,response.body()!!.MAILFEST) // ajout de l'id du festivalier en session

                val intent = Intent(this@inscription, ConsultationEventOffline()::class.java)
                startActivity(intent) // passage à la liste de events

            }

            override fun onFailure(call: Call<Festivalier>, t: Throwable) {
                Log.d("dommage", t.toString())

            }
        })
    }

    fun fillSession(apiService: ApiService, mail:String){

        apiService.getByMail(mail).enqueue(object : Callback<Int>{ // recupération de l'id du festivalier avec son mail
            override fun onResponse(call: Call<Int>, response: Response<Int>) {

                val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
                val session = SharedPreferences.edit()
                val id = response.body()
                if (id != null) {
                    session.putInt("ID", id ).apply() //passage de l'id en session
                }


            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("dommage", t.toString())

            }
        })






    }
}



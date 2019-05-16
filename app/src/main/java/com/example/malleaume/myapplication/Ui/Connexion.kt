package com.example.malleaume.myapplication.Ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.afollestad.vvalidator.form
import com.example.malleaume.myapplication.ApiService
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Festivalier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Connexion : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        val EtLogin = findViewById<EditText>(R.id.etLogin)
        val EtMdp = findViewById<EditText>(R.id.etMdp)
        val apiService = ApiService.Builder.instance
        val SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)



        form{
            input(EtLogin) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                isEmail().description("Doit correspondre à un email, ex : exemple@ex.fr")
            }
            input(EtMdp) {
                isNotEmpty().description("Ce champ ne peut pas être vide")
                length().atLeast(6).description("Ce champ doit contenir un minimum de 6 caractères")
            }
            submitWith(R.id.btnConnexion){ result ->
                val fest = Festivalier(null, null, null ,EtLogin.text.toString(), EtMdp.text.toString())
                    apiService.connexion(fest).enqueue(object : Callback<Festivalier> {
                        override fun onResponse(call: Call<Festivalier>, response: Response<Festivalier>) {
                            Log.d("nif", "test")
                            Toast.makeText(
                                this@Connexion, "Vous êtes connecté!",
                                Toast.LENGTH_LONG
                            ).show()
                            val session =SharedPreferences.edit()
                            val id = response.body()!!.IDFEST
                            if (id != null) {
                                session.putInt("ID", id).apply()
                            }
                            val intent = Intent(this@Connexion, ConsultationEventOffline()::class.java)
                            startActivity(intent)

                        }

                        override fun onFailure(call: Call<Festivalier>, t: Throwable) {
                            Log.d("dommage", t.toString())
                            Toast.makeText(
                                this@Connexion, "Les identifiants que vous avez entrés ne semble pas être bon, veuillez réessayer",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

            }
        }
        }
}

package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    var city: EditText? = null
    var get: Button? = null
    var wether: TextView? = null
    var hum: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        city = findViewById(R.id.citys)
        get = findViewById(R.id.get)
        wether = findViewById(R.id.wether)
        hum = findViewById(R.id.hum)
        getWether()

    }

    private fun getWether() {
        get!!.setOnClickListener {
            var getCity = city!!.text.toString()
            val url = "https://api.openweathermap.org/data/2.5/weather?q="+getCity+"&appid=88ae7d8be806612f9e4f27151f9ae7d3"
            val jsonObjectRequest =
                JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        try {
                            val jsonObject = response.getJSONObject("main")
                            val temp = jsonObject.getString("temp")
                            val t = temp.toDouble() - 273.1
                            wether!!.text = t.toString().substring(0, 4)
                            val h=jsonObject.getString("humidity")
                            hum!!.text=h.toString()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                ) { Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show() }
            MySingleton.getInstance(this@MainActivity).addToRequestQueue(jsonObjectRequest)
        }
    }
}


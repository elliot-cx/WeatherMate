package com.elliot.weathermate.views.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.*
import com.elliot.weathermate.views.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WeatherAdapter.ItemClickListener {

    lateinit var adapter: WeatherAdapter
    var lastUpdateTime: Long = System.currentTimeMillis() - (2 * 60 * 1000)
    lateinit var snackbarUpdate: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snackbarUpdate = Snackbar.make(this.layout,"Erreur de connexion",Snackbar.LENGTH_LONG)

        // Initialisation de la RecyclerView
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = WeatherAdapter()
        adapter.setClickListener(this)
        recycleView.adapter = adapter

        // Autocomplétion de recherche de villes
        val autoCompleteHandler =  Handler(Looper.myLooper()!!)
        val autoAdapter: ArrayAdapter<Geocode> = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,
            mutableListOf())
        editTextSearchCity.setAdapter(autoAdapter)

        editTextSearchCity.addTextChangedListener{
            autoCompleteHandler.removeMessages(0)
            autoCompleteHandler.postDelayed( Runnable{
                if (editTextSearchCity.text.length > 1){
                    GeocodingAPIService.getGeocodes(
                        editTextSearchCity.text.toString(),
                        {
                            autoAdapter.clear()
                            autoAdapter.addAll(it)
                            autoAdapter.filter.filter(editTextSearchCity.text, null);
                        },
                        {
                            snackbarUpdate = Snackbar.make(this.layout,"Erreur de connexion",Snackbar.LENGTH_LONG)
                            snackbarUpdate.show()
                        }
                    )
                }
            },500)
        }

        // Lorsque l'on clique sur une suggestion
        editTextSearchCity.setOnItemClickListener { _, _, position, _ ->
            val geocode = autoAdapter.getItem(position)
            WeatherAPIService.getWeather(geocode!!.name,{
                editTextSearchCity.text.clear()
                editTextSearchCity.clearFocus()
                val intent = Intent(this,DetailActivity::class.java)
                intent.putExtra("weather",it)
                startActivity(intent)
            },{})
        }

    }

    // Gère les clicks effectués sur les weather cards
    override fun onItemClick(view: View?, position: Int) {
        editTextSearchCity.clearFocus()
        val item = Utils.weathers[position]
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("weather",item)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        //TODO clean code
        //On regarde si cela fait plus de 2 minutes que les informations ne sont pas à jour
        val currentTime = System.currentTimeMillis()
        if (currentTime > lastUpdateTime + (2 * 60 * 1000)){
            lastUpdateTime = currentTime
            snackbarUpdate = Snackbar.make(
                this.layout,
                "Mise à jours des données en cours...",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbarUpdate.show()
            // On met à jour toutes les informations
            Utils.weathers.map { weather ->
                if (weather.isGPS){
                    Utils.getLocation(this,this) { location ->
                        val lat = location.latitude.toFloat()
                        val lon = location.longitude.toFloat()
                        WeatherAPIService.getWeatherByCoords(lat,lon,{
                            weather.apply {
                                this.name = it.name
                                this.dt = it.dt
                                this.clouds = it.clouds
                                this.weather = it.weather
                                this.weatherInfo = it.weatherInfo
                                this.wind = it.wind
                                this.coord = it.coord
                            }
                            adapter.notifyItemChanged(Utils.weathers.indexOf(weather))
                            Utils.saveLocations(this)
                            snackbarUpdate.dismiss()
                        },{
                            snackbarUpdate = Snackbar.make(
                                this.layout,
                                "Certaines informations n'ont pas pu être mises à jours !",
                                Snackbar.LENGTH_LONG
                            )
                            snackbarUpdate.show()
                        })
                    }
                }else{
                    WeatherAPIService.getWeather(weather.name,{
                        weather.apply {
                            this.dt = it.dt
                            this.clouds = it.clouds
                            this.weather = it.weather
                            this.weatherInfo = it.weatherInfo
                            this.wind = it.wind
                        }
                        adapter.notifyItemChanged(Utils.weathers.indexOf(weather))
                        Utils.saveLocations(this)
                    },{
                        snackbarUpdate = Snackbar.make(
                            this.layout,
                            "Certaines informations n'ont pas pu être mises à jours !",
                            Snackbar.LENGTH_LONG
                        )
                        snackbarUpdate.show()
                    })
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        snackbarUpdate.dismiss()
        //TODO optimize location
    }
}
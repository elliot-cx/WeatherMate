package com.elliot.weathermate.views.main

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
import com.elliot.weathermate.data.Geocode
import com.elliot.weathermate.data.GeocodingAPIService
import com.elliot.weathermate.data.WeatherAPIService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WeatherAdapter.ItemClickListener {

    lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation de la RecyclerView
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = WeatherAdapter()
        adapter.setClickListener(this)
        recycleView.adapter = adapter

        // Ajout manuel
        arrayListOf("Paris","Villeurbanne","Toronto","Vienne","Lyon","Fosses").map {
            WeatherAPIService.getWeather(
                it,
                {weather ->
                    Utils.weathers.add(weather)
                    adapter.notifyItemInserted(Utils.weathers.indexOf(weather))
                },
                {
                    Toast.makeText(this,"Erreur de connexion", Toast.LENGTH_SHORT).show()
                }
            )
        }

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
                            Toast.makeText(
                                this@MainActivity,
                                "Erreur de connexion",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            },500)
        }

        editTextSearchCity.setOnItemClickListener { _, _, position, _ ->
            Log.i("TEST", autoAdapter.getItem(position).toString() )
        }

    }

    override fun onResume() {
        super.onResume()
    }

    // Gère les clicks effectués sur les weather cards
    override fun onItemClick(view: View?, position: Int) {
        val item = Utils.weathers[position]
        Log.i("Test",item.toString())

        item.update(layout)
        adapter.notifyItemChanged(position)

        //WeatherAPIService.getWeather(
        //    item.name,
        //    {
        //        Utils.weathers[position] = it
        //        adapter.notifyItemChanged(position)
        //    },
        //    {

        //    }
        //)
    }

}
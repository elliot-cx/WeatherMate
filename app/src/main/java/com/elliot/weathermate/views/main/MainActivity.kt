package com.elliot.weathermate.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
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
        arrayListOf("Paris","Toronto","Vienne","Lyon","Fosses").map {
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
    }

    override fun onItemClick(view: View?, position: Int) {
        val item = Utils.weathers[position]
        WeatherAPIService.getWeather(
            item.name,
            {
                Utils.weathers[position] = it
                adapter.notifyItemChanged(position)
            },
            {
                Toast.makeText(this,"Erreur de connexion", Toast.LENGTH_SHORT).show()
            }
        )
    }

}
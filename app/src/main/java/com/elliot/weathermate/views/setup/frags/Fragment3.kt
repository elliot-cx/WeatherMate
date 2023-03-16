package com.elliot.weathermate.views.setup.frags

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.WeatherAPIService
import com.elliot.weathermate.views.setup.SetupActivity
import kotlinx.android.synthetic.main.fragment3.*


class Fragment3 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val setupActivity = activity as SetupActivity
        authoriseButton.setOnClickListener{ _ ->
            if(Utils.requestLocationPermission(setupActivity.applicationContext,setupActivity)){
                setupActivity.setAnimationByName(animation,"locationsearch")
                Utils.getLocation(setupActivity.applicationContext, setupActivity) {
                    setupActivity.setAnimationByName(animation,"locationfound")
                    WeatherAPIService.getWeatherByCoords(it.latitude.toFloat(),
                        it.longitude.toFloat(),{ data ->
                        data.isGPS = true
                        Utils.weathers.add(data)
                        setupActivity.finishConfig()
                    },{
                        // Vérifier la connexion internet / Réssayer
                    })
                }
            }
            setupActivity.permissionChanged = {
                if (it){
                    authoriseButton.text = Resources.getSystem().getString(R.string.next)
                    authoriseButton.setOnClickListener { _ ->
                        //initialiser l'application
                        setupActivity.setAnimationByName(animation,"locationsearch")
                        Utils.getLocation(setupActivity.applicationContext, setupActivity) { location ->
                            setupActivity.setAnimationByName(animation,"locationfound")
                            WeatherAPIService.getWeatherByCoords(location.latitude.toFloat(),
                                location.longitude.toFloat(),{ data ->
                                    data.isGPS = true
                                    Utils.weathers.add(data)
                                    setupActivity.finishConfig()
                                },{
                                    // Vérifier la connexion internet / Réssayer
                                })
                        }
                    }
                }
            }
        }
    }
}
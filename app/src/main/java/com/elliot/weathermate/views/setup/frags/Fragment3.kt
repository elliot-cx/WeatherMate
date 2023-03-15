package com.elliot.weathermate.views.setup.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elliot.weathermate.R
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
        authoriseButton.setOnClickListener{// locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(setupActivity.requestLocationPermission()){

                setupActivity.setAnimationByName(animation,"locationsearch")
                setupActivity.getLocation {
                    Log.i("TEST",it.toString())
                    setupActivity.setAnimationByName(animation,"locationfound")
                }
            }
            setupActivity.permissionChanged = {
                if (it){
                    authoriseButton.text = "Suivant"
                    authoriseButton.setOnClickListener {
                        //initialiser l'application
                    }
                }
            }
        }
    }


}
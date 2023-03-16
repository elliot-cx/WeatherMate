package com.elliot.weathermate.views.setup.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.tayfuncesur.stepper.Stepper
import kotlinx.android.synthetic.main.fragment2.*

class Fragment2 : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            if(metricsButton.isChecked){
                Utils.units = "metric"
            }
            else{
                Utils.units = "imperial"
            }
            view.findNavController().navigate(R.id.fragment2to3)
            activity?.findViewById<Stepper>(R.id.stepper)?.forward()
        }

    }

}
package com.elliot.weathermate.views.setup.frags

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.elliot.weathermate.R
import com.tayfuncesur.stepper.Stepper
import kotlinx.android.synthetic.main.fragment1.*
import androidx.navigation.findNavController

class Fragment1 : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.fragment1to2)
            activity?.findViewById<Stepper>(R.id.stepper)?.forward()
        }

    }


}
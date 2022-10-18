package com.example.m.ismayilov.timetracker.open

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {

    var view: FrameLayout? = null
    lateinit var animation :Animation
    lateinit var binding: FragmentSplashScreenBinding


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater , container, false)
        view = binding.root

        animation = AnimationUtils.loadAnimation(activity, R.anim.alpha_anim)
        binding.splashimageName.animation = animation
        binding.splashimageLogo.animation = animation

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController(view!!).navigate(R.id.action_splashScreen_to_createFragment)
        }, 2500)

        return view
    }
}
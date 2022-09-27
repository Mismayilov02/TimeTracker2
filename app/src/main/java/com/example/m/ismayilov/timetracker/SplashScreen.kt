package com.example.m.ismayilov.timetracker

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
        binding.imageView.animation = animation


        Handler(Looper.getMainLooper()).postDelayed({
            findNavController(view!!).navigate(R.id.navigation_splast_tologin)
        }, 3000)

        return view
    }
}
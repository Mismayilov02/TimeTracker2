package com.example.m.ismayilov.timetracker.open

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.m.ismayilov.timetracker.*
import com.example.m.ismayilov.timetracker.databinding.FragmentSplashScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class SplashScreen : Fragment() {

    var view: FrameLayout? = null
    lateinit var animation :Animation
    lateinit var binding: FragmentSplashScreenBinding
    lateinit var sharedPreferencesManager: SharedPreferencesManager


    @SuppressLint("UseRequireInsteadOfGet", "NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater , container, false)
        view = binding.root
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        animation = AnimationUtils.loadAnimation(activity, R.anim.alpha_anim)
        binding.splashimageName.animation = animation
        binding.splashimageLogo.animation = animation

        toDateCheck()

        Handler(Looper.getMainLooper()).postDelayed({
            if (ArtelDialog().isConnected(requireContext())) sellectSplashToId()
            else ArtelDialog().getWifiDialog(requireContext() , requireActivity())
        }, 2500)

        return view
    }

    fun sellectSplashToId() {
        if (sharedPreferencesManager.getBoolean("login", false)!!) {
            checkUser()

        } else if (sharedPreferencesManager.getBoolean("create", false)!!) {
            val direction = SplashScreenDirections.navigationSplastTologin(true)
            findNavController().navigate(direction)
        } else {
            findNavController().navigate(R.id.action_splashScreen_to_createFragment)
        }
    }

    fun checkUser(){
        var sorgu  = FirebaseDatabase.getInstance().getReference("users/").orderByChild("phone")
            .equalTo(sharedPreferencesManager.getString("phone", "notFoundUser"))
        sorgu.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()) {
                   startActivity(Intent(requireContext(), BaseActivity::class.java))
               }else{
                   findNavController().navigate(R.id.action_splashScreen_to_createFragment)
               }

                sorgu.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "value,jyck.email ", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toDateCheck(){
        if(!sharedPreferencesManager.getString("todate" , "1111.11.11").equals(Constant().simpleToDay.format(Date().time))){
            sharedPreferencesManager.setValue("todate" , Constant().simpleToDay.format(Date().time))
        }
    }
}
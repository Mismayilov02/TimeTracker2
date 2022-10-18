package com.example.m.ismayilov.timetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.m.ismayilov.timetracker.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {


    lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        val  view = binding.root
        setContentView(view)

        binding.runMenuBtn2.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }

        val navHost = supportFragmentManager.findFragmentById(R.id.navhost) as NavHostFragment

        NavigationUI.setupWithNavController(binding.navigationview, navHost.navController)
    }
}
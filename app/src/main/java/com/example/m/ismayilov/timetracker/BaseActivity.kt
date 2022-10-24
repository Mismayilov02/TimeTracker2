package com.example.m.ismayilov.timetracker

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.m.ismayilov.timetracker.databinding.ActivityBaseBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase.getInstance

class BaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityBaseBinding
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        val  view = binding.root
        setContentView(view)
        sharedPreferencesManager  = SharedPreferencesManager(this)

        binding.runMenuBtn2.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }

        val navHost = supportFragmentManager.findFragmentById(R.id.navhost) as NavHostFragment
        NavigationUI.setupWithNavController(binding.navigationview, navHost.navController)
        var menu = binding.navigationview.menu

        menu.findItem(R.id.time).setTitle(sharedPreferencesManager.getString("todate" , "1111.11.11"))

        if (!sharedPreferencesManager.getBoolean("admin" , true)!!){
            menu.findItem(R.id.permission).isVisible = false
            menu.findItem(R.id.onlinenavhost).isVisible = false
        }



    }
}
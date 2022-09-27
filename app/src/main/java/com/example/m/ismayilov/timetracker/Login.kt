package com.example.m.ismayilov.timetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.m.ismayilov.timetracker.databinding.FragmentLoginBinding

class Login : Fragment() {

    lateinit var binding: FragmentLoginBinding
    var view :FrameLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        view = binding.root

        binding.loginBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.navigation_login_torunscreen)
        }
        return view
    }


}
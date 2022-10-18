package com.example.m.ismayilov.timetracker.open

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.m.ismayilov.timetracker.BaseActivity
import com.example.m.ismayilov.timetracker.R
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
        getSuccesReportDialog()
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(requireContext() , BaseActivity::class.java))
        }
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun getSuccesReportDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }


}
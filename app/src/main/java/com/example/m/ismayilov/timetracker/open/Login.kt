package com.example.m.ismayilov.timetracker.open

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.m.ismayilov.timetracker.*
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.databinding.FragmentLoginBinding
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class Login : Fragment() {

    lateinit var binding: FragmentLoginBinding
    var password:String = ""
    var admin = false
    var view :FrameLayout? = null
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var dialog: Dialog
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        view = binding.root
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")

        val args: LoginArgs by navArgs()
        if(args.wait){
            getChechInfo()
        }

        binding.loginCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_createFragment)
        }

        binding.loginBtn.setOnClickListener {
            textEmptyCheck()

        }
        return view
    }

    fun textEmptyCheck(){
        var isEmpty  = true
        if(binding.loginPhone.text.isNullOrEmpty()){
            isEmpty =  false
            binding.loginPhone.error  =  "Phone required"
            binding.loginPhone.requestFocus()
        }
        if(binding.loginPassword.text.isNullOrEmpty()){
            isEmpty =  false
            binding.loginPassword.error  =  "Name required"
            binding.loginPassword.requestFocus()
        }

        if (isEmpty){
            sharedPreferencesManager.setValue("phone" , binding.loginPhone.text.toString())
            login()
        }

    }


    fun login(){
        var sorgu  = firebase!!.orderByChild("phone").equalTo(binding.loginPhone.text.toString())

        sorgu.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (i in snapshot.children){
                    try{
                        val value = i.getValue(Users::class.java)
                        password = value!!.password
                        admin = value.admin
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                if (snapshot.exists() && password.equals(binding.loginPassword.text.toString())){
                    if (admin){ sharedPreferencesManager.setValue("admin", true) }
                    sharedPreferencesManager.setValue("login" , true)
                    startActivity(Intent(requireContext() , BaseActivity::class.java))
                    requireActivity().finish();
                }else{
                    Snackbar.make(view!!, "Numara ve ya Sifre Yalnis" , Snackbar.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getChechInfo(){
       dialog()
        var sorgu  = firebase!!.orderByChild("phone")
        .equalTo(sharedPreferencesManager.getString("phone", "notFoundUser"))
        sorgu.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    try{
                        val value = i.getValue(Users::class.java)
                        if (value != null) {
                            if(value.permission){
                                dialog.dismiss()
                            }
                        }
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }

                if (!snapshot.exists()) {
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_login_to_createFragment)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "value,jyck.email ", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun dialog(){
         dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }
}
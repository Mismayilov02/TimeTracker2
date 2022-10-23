package com.example.m.ismayilov.timetracker.open

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.m.ismayilov.timetracker.ArtelDialog
import com.example.m.ismayilov.timetracker.BaseActivity
import com.example.m.ismayilov.timetracker.SharedPreferencesManager
import com.example.m.ismayilov.timetracker.Users
import com.example.m.ismayilov.timetracker.databinding.FragmentCreateBinding
import com.google.firebase.database.*


class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var artelDialog: ArtelDialog
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    lateinit var view: FrameLayout
    var checkUser = false
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentCreateBinding.inflate(inflater ,container, false)
        view = binding.root
        artelDialog = ArtelDialog()
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")

        binding.createHaveAccount.setOnClickListener {
//            startActivity(Intent(requireContext() , BaseActivity::class.java ))
            val direction = CreateFragmentDirections.actionCreateFragmentToLogin(false)
            findNavController().navigate(direction)
        }
        binding.createBtn.setOnClickListener {
            var isEmpty  = true
            if(binding.createPhone.text.isNullOrEmpty()){
                isEmpty =  false
                binding.createPhone.error  =  "Phone required"
                binding.createPhone.requestFocus()
            }
            if(binding.createName.text.isNullOrEmpty()){
                isEmpty =  false
                binding.createName.error  =  "Name required"
                binding.createName.requestFocus()
            }
            if(binding.createPassword.text.isNullOrEmpty()){
                isEmpty =  false
//                binding.createPassword.error  =  "Password required"
                binding.createPassword.requestFocus()
            }

            if (artelDialog.isConnected(requireContext())){
                if (isEmpty) {
                    checkUser = true
                    sharedPreferencesManager.setValue("phone" ,binding.createPhone.text.toString() )
                    sharedPreferencesManager.setValue("name" ,binding.createName.text.toString() )
                    sharedPreferencesManager.setValue("password" ,binding.createPassword.text.toString() )
                    chechkFirebaseInformation(binding.createPhone.text.toString())
                }
            }else{
                artelDialog.getWifiDialog(requireContext())
            }
        }

        return  view
    }

    fun sendValuesFirebase(){
        FirebaseDatabase.getInstance().getReference("users").child( binding.createPhone.text.toString()).setValue(
            Users(
                binding.createName.text.toString(),
                binding.createPhone.text.toString(),
                binding.createPassword.text.toString(),
                false,false
            )
        )
        sharedPreferencesManager.setValue("create" , true)
        val direction = CreateFragmentDirections.actionCreateFragmentToLogin(true)
        findNavController().navigate(direction)
    }

    fun chechkFirebaseInformation(number:String) {
        var sorgu = firebase!!.orderByChild("phone").equalTo(binding.createPhone.text.toString())
        sorgu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (checkUser){
                    when (snapshot.exists()) {
                        true -> {
                            artelDialog.getHaveDialog(requireContext(), view)
                        }
                        false -> {
                            sendValuesFirebase()
                        }
                    }
                    checkUser = false
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}
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
import com.example.m.ismayilov.timetracker.*
import com.example.m.ismayilov.timetracker.databinding.FragmentCreateBinding
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging


class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var artelDialog: ArtelDialog
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    lateinit var view: FrameLayout
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
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        binding.createHaveAccount.setOnClickListener {
//            startActivity(Intent(requireContext() , BaseActivity::class.java ))
            val direction = CreateFragmentDirections.actionCreateFragmentToLogin(false)
            findNavController().navigate(direction)
        }
        binding.createBtn.setOnClickListener {
            var isEmpty  = true
            if (binding.createPhone.text?.isEmpty() == false) {
                if (binding.createPhone.text!!.length < 9) {
                    isEmpty =  false
                    binding.createPhone.error = "Phone Lengs Error"
                    binding.createPhone.requestFocus()
                }
            }


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
                    var phone:String = binding.createPhone.text!!.substring(binding.createPhone.text!!.length-9 ,binding.createPhone.text!!.length)
                    sharedPreferencesManager.setValue("phone" ,phone)
                    sharedPreferencesManager.setValue("name" ,binding.createName.text.toString() )
                    sharedPreferencesManager.setValue("password" ,binding.createPassword.text.toString() )

                    chechkFirebaseInformation(phone)
                }
            }else{
                artelDialog.getWifiDialog(requireContext() , requireActivity())
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
                false,false , null, null
            )
        )
        FCM().pushNofication(requireContext(),"New User" , binding.createName.toString() )
        sharedPreferencesManager.setValue("create" , true)
        val direction = CreateFragmentDirections.actionCreateFragmentToLogin(true)
        findNavController().navigate(direction)
    }

    fun chechkFirebaseInformation(phone:String) {
        var sorgu = firebase!!.orderByChild("phone").equalTo(phone)
        sorgu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                    when (snapshot.exists()) {
                        true -> {
                            artelDialog.getHaveDialog(requireContext(), view)
                        }
                        false -> {
                            sendValuesFirebase()
                        }
                    }
                sorgu.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}
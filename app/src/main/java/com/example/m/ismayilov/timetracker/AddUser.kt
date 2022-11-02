package com.example.m.ismayilov.timetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.m.ismayilov.timetracker.databinding.FragmentAddUserBinding
import com.example.m.ismayilov.timetracker.unkonown.ArtelDialog
import com.google.firebase.database.FirebaseDatabase

class AddUser : Fragment() {

    lateinit var binding:FragmentAddUserBinding
    lateinit var view: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAddUserBinding.inflate(inflater, container, false)
        view = binding.root

        binding.addAddBtn.setOnClickListener {
            var isEmpty  = true

            if (binding.addPhone.text?.isEmpty() == false) {
                if (binding.addPhone.text!!.length < 9) {
                    isEmpty =  false
                    binding.addPhone.error = "Phone Lengs Error"
                    binding.addPhone.requestFocus()
                }
            }

               if(binding.addPhone.text.isNullOrEmpty() ){
                   isEmpty =  false
                   binding.addPhone.error  =  "Phone required"
                   binding.addPhone.requestFocus()
               }


            if(binding.addName.text.isNullOrEmpty()){
                isEmpty =  false
                binding.addName.error  =  "Name required"
                binding.addName.requestFocus()
            }
            if(binding.addPassword.text.isNullOrEmpty()){
                isEmpty =  false
                binding.addPassword.requestFocus()
            }

            if (ArtelDialog().isConnected(requireContext())){
                if (isEmpty) {
                    sendValuesFirebase()
                }
            }else{
                ArtelDialog().getWifiDialog(requireContext())
            }
        }

        return view
    }

    fun sendValuesFirebase(){

        var phone:String = binding.addPhone.text!!.substring(binding.addPhone.text!!.length-9 ,binding.addPhone.text!!.length)
        FirebaseDatabase.getInstance().getReference("users").child( phone).setValue(
            Users(
                binding.addName.text.toString(),
                phone,
                binding.addPassword.text.toString(),
                false,true ,
               null,null
        )
        )
        findNavController().navigate(R.id.action_addUser_to_runScreen2)
    }

}
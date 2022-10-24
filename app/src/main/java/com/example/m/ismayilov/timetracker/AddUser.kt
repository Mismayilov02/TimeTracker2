package com.example.m.ismayilov.timetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.m.ismayilov.timetracker.databinding.FragmentAddProekBinding
import com.example.m.ismayilov.timetracker.databinding.FragmentAddUserBinding
import com.example.m.ismayilov.timetracker.open.CreateFragmentDirections
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
            if(binding.addPhone.text.isNullOrEmpty()){
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
        FirebaseDatabase.getInstance().getReference("users").child( binding.addPhone.text.toString()).setValue(
            Users(
                binding.addName.text.toString(),
                binding.addPhone.text.toString(),
                binding.addPassword.text.toString(),
                false,true , false
            )
        )
        findNavController().navigate(R.id.action_addUser_to_runScreen2)
    }

}
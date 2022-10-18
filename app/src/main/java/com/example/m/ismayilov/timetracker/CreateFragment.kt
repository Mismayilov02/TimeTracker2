package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.Navigation
import com.example.m.ismayilov.timetracker.databinding.FragmentCreateBinding



class CreateFragment : Fragment() {

lateinit var binding: FragmentCreateBinding
//lateinit var fireBaseDatabase:FirebaseDatabase
//lateinit var firebase:DatabaseReference
lateinit var view: FrameLayout
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentCreateBinding.inflate(inflater ,container, false)
        view = binding.root
//
//     var fireBaseDatabase  = Fir
//       var firebase = fireBaseDatabase.getReference("users")
//
//        binding.createHaveAccount.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_createFragment_to_login)
//        }
        binding.createBtn.setOnClickListener {
//            var isEmpty  = true
//            if(binding.createEmail.text.isNullOrEmpty()){
//                isEmpty =  false
//                binding.createEmail.error  =  "Email required"
//                binding.createEmail.requestFocus()
//            }
//            if(binding.createName.text.isNullOrEmpty()){
//                isEmpty =  false
//                binding.createName.error  =  "Name required"
//                binding.createName.requestFocus()
//            }
//            if(binding.createPassword.text.isNullOrEmpty()){
//                isEmpty =  false
//                binding.createPassword.error  =  "Password required"
//                binding.createPassword.requestFocus()
//            }
//
//            if(isEmpty){
//                fireBaseDatabase.setValue(Users(binding.createName.text.toString(),
//                    binding.createEmail.text.toString(), binding.createPassword.text.toString()))

//            }

          startActivity(Intent(requireContext() , BaseActivity::class.java ))

        }

        return  view
    }

}
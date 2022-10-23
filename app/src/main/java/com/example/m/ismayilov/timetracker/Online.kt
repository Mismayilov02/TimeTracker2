package com.example.m.ismayilov.timetracker

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.adapter.KatagoryRecycleAdapter
import com.example.m.ismayilov.timetracker.databinding.FragmentOnlineBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Online : Fragment() {

    lateinit var binding:FragmentOnlineBinding
    lateinit var view: FrameLayout

    lateinit var onlineAdapter: OnlineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentOnlineBinding.inflate(inflater, container, false)
        view = binding.root

        var fireBaseDatabase  = FirebaseDatabase.getInstance()
        var firebase = fireBaseDatabase.getReference("users/")

//        ArtelDialog().getSuccesReportDialog(requireContext())

        var sorgu  = firebase.child("0504835648")

        firebase.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<Users>()
                for (i in snapshot.children){
                    try{
                        val value = i.getValue(Users::class.java)
                        if (value != null) {
                            val users = Users(value.name , value.phone , "" , value.online ,false)
                            list.add(users)

                        } else {
                            Toast.makeText(requireContext(), "nullc", Toast.LENGTH_SHORT).show()
                        }
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }

                if(list.size !=0){
                    binding.onlineIsemptyText.isVisible = false
                }
                setAdapter(list)

            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
                Toast.makeText(requireContext(), "value.email ", Toast.LENGTH_SHORT).show()
            }

        })


        return view
    }

    fun setAdapter(lists:MutableList<Users>){
        onlineAdapter = OnlineAdapter(requireContext() , lists)
        binding.onlineRecyclerview.setHasFixedSize(true)
        binding.onlineRecyclerview.setLayoutManager(GridLayoutManager(activity, 1))
        binding.onlineRecyclerview.adapter = onlineAdapter
    }

}
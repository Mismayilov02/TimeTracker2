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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.databinding.FragmentOnlineBinding
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.onClick.OnlineOnClickLIstener
import com.google.firebase.database.*

class Online : Fragment()  , OnlineOnClickLIstener {

    lateinit var binding:FragmentOnlineBinding
    lateinit var view: FrameLayout
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var onlineAdapter: OnlineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentOnlineBinding.inflate(inflater, container, false)
        view = binding.root

         fireBaseDatabase  = FirebaseDatabase.getInstance()
         firebase = fireBaseDatabase!!.getReference("users/")

        firebase!!.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<Users>()
                for (i in snapshot.children){
                    try{
                        val value = i.getValue(Users::class.java)
                        if (value != null) {
                            list.add(value)

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
        onlineAdapter = OnlineAdapter(requireContext(),this@Online , lists)
        binding.onlineRecyclerview.setHasFixedSize(true)
        binding.onlineRecyclerview.setLayoutManager(GridLayoutManager(activity, 1))
        binding.onlineRecyclerview.adapter = onlineAdapter
    }

    override fun onClickListenerId(id: Int, play: Boolean) {

    }

    override fun onClickListenerAction(phone: String) {
        val direction = OnlineDirections.actionOnlinenavhostToAddProek2("NSP", phone, "online" )
        findNavController().navigate(direction)
    }

    override fun onClickSetHistory(phone: String) {
        val direction = OnlineDirections.actionOnlinenavhostToHistoryFragment(true, phone)
        findNavController().navigate(direction)
    }

    override fun onClickSetDeleteUser(phone: String) {
        firebase!!.child(phone).removeValue()
    }

    override fun onClickSetDeleteUserProject(phone: String, projectName: String) {
        firebase!!.child(phone).child("project").child(projectName).removeValue()
    }

    override fun onClickSetUserChangePojectName(phone: String , projectName: String) {
        val direction = OnlineDirections.actionOnlinenavhostToAddProek2("NSP", phone = phone, "onlineUpdateProject" , projectName = projectName )
        findNavController().navigate(direction)
    }


}
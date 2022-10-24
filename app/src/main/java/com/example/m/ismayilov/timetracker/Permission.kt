package com.example.m.ismayilov.timetracker

import android.content.ContentValues
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
import com.example.m.ismayilov.timetracker.databinding.FragmentPermissionBinding
import com.google.firebase.database.*

class Permission : Fragment()  ,FirebaseUpdateOnClick {

    lateinit var binding: FragmentPermissionBinding
    lateinit var view: FrameLayout
    var  list = mutableListOf<Users>()
    lateinit var permissionAdapter: PermissionAdapter
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentPermissionBinding.inflate(inflater, container, false)
        view = binding.root

       fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")

        var sorgu  = firebase!!.orderByChild("permission").equalTo(false)

        sorgu.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                 list = mutableListOf<Users>()
                for (i in snapshot.children){
                    try{
                        val value = i.getValue(Users::class.java)
                        if (value != null) {
                            val users = Users(value.name , value.phone , "" , value.online ,value.permission , value.admin)
                            list.add(users)

                        } else {
                            Toast.makeText(requireContext(), "nullc", Toast.LENGTH_SHORT).show()
                        }
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }

                setAdapter(list)

            }
            override fun onCancelled(error: DatabaseError) {
            }

        })


        return view;
    }

    fun setAdapter(lists:MutableList<Users>){
        if (lists.size != 0 ){
            binding.permissionEmpty.isVisible = false
        }
        permissionAdapter = PermissionAdapter(requireContext() , lists , this@Permission)
        binding.permissionRecyclerview.setHasFixedSize(true)
        binding.permissionRecyclerview.setLayoutManager(GridLayoutManager(activity, 1))
        binding.permissionRecyclerview.adapter = permissionAdapter
    }

    override fun onClickListenerId(id: Int, permission: Boolean) {
        if (permission){
            try {
                val updateValue = HashMap<String, Any>()
                updateValue["permission"] = permission
                firebase!!.child(list[id].phone).updateChildren(updateValue)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }else{
            firebase!!.child(list[id].phone).removeValue()
        }
    }

}
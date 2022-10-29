package com.example.m.ismayilov.timetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.onClick.FirebaseUpdateOnClick

class PermissionAdapter(val context: Context, val list: MutableList<Users>   , val firebaseUpdateOnClick: FirebaseUpdateOnClick):RecyclerView.Adapter<PermissionAdapter.cardViewDesign>() {

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var name: TextView
        var phone :TextView
        var yes:ImageView
        var no:ImageView

        init {
            name = view.findViewById(R.id.permission_name_text)
            phone= view.findViewById(R.id.permission_phone)
            yes= view.findViewById(R.id.permission_yes)
            no= view.findViewById(R.id.permission_no)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.permission_recycler_design , parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.name.text = list.get(position).name
        holder.phone.text = list.get(position).phone

        holder.yes.setOnClickListener {
          firebaseUpdateOnClick.onClickListenerId(position , true)
        }

        holder.no.setOnClickListener {
            firebaseUpdateOnClick.onClickListenerId(position , false)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



}
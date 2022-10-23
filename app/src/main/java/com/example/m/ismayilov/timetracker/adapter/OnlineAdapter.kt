package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.HistoryColor

class OnlineAdapter(val context: Context , val list: MutableList<Users>  ):RecyclerView.Adapter<OnlineAdapter.cardViewDesign>() {

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var name: TextView
        var phone :TextView
        var isOnline:ImageView

        init {
            name = view.findViewById(R.id.online_name_text)
            phone= view.findViewById(R.id.online_phone)
            isOnline= view.findViewById(R.id.online_onlineview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.online_recycler_design , parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        if(list.get(position).online)
            holder.isOnline.setColorFilter(ContextCompat.getColor(context, R.color.green))

        holder.name.text = list.get(position).name
        holder.phone.text = list.get(position).phone
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
package com.example.m.ismayilov.timetracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.UserDeaultProjectName
import com.example.m.ismayilov.timetracker.room.Katagory

class OnlineUserItemAdapter(val context: Context, val list: MutableList<UserDeaultProjectName>):RecyclerView.Adapter<OnlineUserItemAdapter.cardViewDesign>() {

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: ImageView
        var textView :TextView


        init {
            baseColor = view.findViewById(R.id.online_proyekt_color)
            textView= view.findViewById(R.id.online_proyekt_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.online_proyek_design, parent,false)

        return cardViewDesign(design)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.baseColor.setColorFilter(Color.parseColor(list.get(position).colorCode))
        holder.textView.text = list!!.get(position).projectName


    }

    override fun getItemCount(): Int {
        return list!!.size
    }

}
package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.Katagory

class ProyektRecycleAdapter(val context: Context, val katagory: MutableList<Katagory>?):RecyclerView.Adapter<ProyektRecycleAdapter.cardViewDesign>() {

//     var colorLines  =  ArrayList<CardView>()
//    var onItemClick: ((String) -> Unit)? = null
    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: CardView
        var textView :TextView

        init {
            baseColor = view.findViewById(R.id.proyekt_color)
            textView= view.findViewById(R.id.proyekt_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.proyek_design , parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.baseColor.setCardBackgroundColor(Color.parseColor(katagory!!.get(position).color_code))
        holder.textView.text = katagory!!.get(position).project_name
//
//        holder.baseColor.setOnClickListener {
//            for (i in 0..colors.size-1){
//               colorLines.get(i).isVisible = false
//            }
//            holder.colorLine.isVisible = true
//            onItemClick?.invoke(colors.get(position))
//
//        }
    }

    override fun getItemCount(): Int {
        return katagory!!.size
    }

}
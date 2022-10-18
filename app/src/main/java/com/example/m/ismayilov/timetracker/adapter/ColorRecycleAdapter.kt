package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.HistoryColor

class ColorRecycleAdapte(val context: Context , val colors: MutableList<String>  ):RecyclerView.Adapter<ColorRecycleAdapte.cardViewDesign>() {

     var colorLines  =  ArrayList<CardView>()
    var onItemClick: ((String) -> Unit)? = null
    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: CardView
        var colorLine :CardView

        init {
            baseColor = view.findViewById(R.id.color_design_basecolor)
            colorLine= view.findViewById(R.id.color_design_sellect_color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.color_design , parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.baseColor.setCardBackgroundColor(Color.parseColor(colors.get(position)))
        holder.baseColor.tag = position
        holder.colorLine.setCardBackgroundColor(Color.parseColor(colors.get(position)))

        if (position ==1){
            holder.colorLine.isVisible = true
            onItemClick?.invoke(colors.get(position))
        }

        colorLines.add(holder.colorLine)

        holder.baseColor.setOnClickListener {
            for (i in 0..colors.size-1){
               colorLines.get(i).isVisible = false
            }
            holder.colorLine.isVisible = true
            onItemClick?.invoke(colors.get(position))

        }
    }

    override fun getItemCount(): Int {
        return colors.size
    }

}
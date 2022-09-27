package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.ProjektHistory

class RunProyektRecycleAdapter(val context: Context, var proyekts: MutableList<Katagory>  ):RecyclerView.Adapter<RunProyektRecycleAdapter.runProyektViewDesign>() {

//     var colorLines  =  ArrayList<CardView>()
    var onItemClick: ((Int) -> Unit)? = null
    inner class runProyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: CardView
        var name :TextView
        var btn : ImageView

        init {
            color = view.findViewById(R.id.run_proyekt_color)
            name= view.findViewById(R.id.run_proyekt_text)
            btn= view.findViewById(R.id.run_proyekt_play_btn)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): runProyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.proyek_run_design , parent,false)

        return runProyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: runProyektViewDesign, position: Int) {
        holder.name.text = proyekts.get(position).project_name
        holder.color.setCardBackgroundColor(Color.parseColor(proyekts.get(position).color_code))
        holder.btn.setOnClickListener {
            onItemClick?.invoke(proyekts.get(position).id)
//            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return proyekts.size
    }

    fun update(proyekts: MutableList<Katagory>){
        this.proyekts = proyekts
        notifyDataSetChanged()
    }

}
package com.example.m.ismayilov.timetracker.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.room.Katagory

class ProyektRecycleAdapter(val context: Context, val katagory: MutableList<Katagory>? , val onClickLIstener: OnClickLIstener):RecyclerView.Adapter<ProyektRecycleAdapter.cardViewDesign>() {

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: ImageView
        var textView :TextView
        var playBtn:ImageView

        init {
            baseColor = view.findViewById(R.id.proyekt_color)
            textView= view.findViewById(R.id.proyekt_text)
            playBtn = view.findViewById(R.id.proyekt_play_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.proyek_design, parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.baseColor.setColorFilter(Color.parseColor(katagory!!.get(position).color_code))
        holder.textView.text = katagory!!.get(position).project_name

        if (katagory!!.get(position).run) {
             holder.playBtn.setImageResource(R.drawable.pause)
        }else{
            holder.playBtn.setImageResource(R.drawable.play)
        }

        holder.playBtn.setOnClickListener {
            onClickLIstener.onClickListenerId(katagory.get(position).id , katagory.get(position).run)
        }
        holder.textView.setOnClickListener {
            onClickLIstener.onClickListenerId(katagory.get(position).id , katagory.get(position).run)
        }

    }

    override fun getItemCount(): Int {
        return katagory!!.size
    }

}
package com.example.m.ismayilov.timetracker.adapter

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
import com.example.m.ismayilov.timetracker.room.Katagory

class ProyektRecycleAdapter(val context: Context, val katagory: MutableList<Katagory>? , val onClickLIstener: OnClickLIstener):RecyclerView.Adapter<ProyektRecycleAdapter.cardViewDesign>() {

    var holderList  = mutableListOf<cardViewDesign>()
    //    var adapterList  = mutableListOf<cardViewDesign>()
    var i = 0
    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: ImageView
        var textView :TextView
        var playBtn:ImageView
        var edit:ImageView
        var delete:ImageView

        init {
            baseColor = view.findViewById(R.id.proyekt_color)
            textView= view.findViewById(R.id.proyekt_text)
            playBtn = view.findViewById(R.id.proyekt_play_btn)
            edit = view.findViewById(R.id.project_edit)
            delete = view.findViewById(R.id.project_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
        val design  = LayoutInflater.from(context).inflate(R.layout.proyek_design, parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holderList.add(holder)
        holder.baseColor.setColorFilter(Color.parseColor(katagory!!.get(position).color_code))
        holder.textView.text = katagory!!.get(position).project_name

        if (katagory!!.get(position).run) {
            holder.playBtn.setImageResource(R.drawable.pause)
        }else{
            holder.playBtn.setImageResource(R.drawable.play)
        }

        holder.playBtn.setOnClickListener {
            onClickLIstener.onClickListenerId(katagory.get(position))
        }
        holder.textView.setOnClickListener {
            onClickLIstener.onClickListenerId(katagory.get(position))
        }

        holder.edit.setOnClickListener {
            onClickLIstener.onClickSetEditName(katagory.get(position).project_name , katagory.get(position).katagory_name)
        }

        holder.delete.setOnClickListener {
            onClickLIstener.onClickSetDelete(katagory.get(position).katagory_name , katagory.get(position).project_name)
        }

    }

    override fun getItemCount(): Int {
        return katagory!!.size
    }

    fun editVisible(visibility:Boolean){
        for (i in holderList){
            if (visibility) {
                i.edit.visibility = View.VISIBLE
                i.delete.visibility = View.VISIBLE
                i.playBtn.visibility = View.INVISIBLE
            } else {
                i.edit.visibility = View.GONE
                i.delete.visibility = View.GONE
                i.playBtn.visibility = View.VISIBLE
            }
        }
    }


}
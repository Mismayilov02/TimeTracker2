package com.example.m.ismayilov.timetracker.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.room.RunHistory
import java.util.*
import java.util.concurrent.TimeUnit

class HistoryAdapter(val context: Context, val list: MutableList<RunHistory>?):RecyclerView.Adapter<HistoryAdapter.cardViewDesign>() {

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: ImageView
        var textView :TextView
        var totalTextView :TextView

        init {
            baseColor = view.findViewById(R.id.history_color)
            textView= view.findViewById(R.id.history_text)
            totalTextView = view.findViewById(R.id.history_total_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.history_design, parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        holder.baseColor.setColorFilter(Color.parseColor(list!!.get(position).color_code))
        holder.textView.text = list!!.get(position).project_name

        var runTime:Long = 0
        if(list.get(position).play) runTime = Date().time - list.get(position).start_date
        val saat = TimeUnit.MILLISECONDS.toHours(list.get(position).daily_total+ runTime) % 24
        val dakika = TimeUnit.MILLISECONDS.toMinutes(list.get(position).daily_total+ runTime) % 60
        val saniye = TimeUnit.MILLISECONDS.toSeconds(list.get(position).daily_total + runTime) % 60

        holder.totalTextView.text =  ("${timeToString(saat)}:${timeToString(dakika)}:${timeToString(saniye)}")


    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun timeToString(time:Long):String{
        if(time<10) {
            return "0$time"
        }
        return "$time"
    }

}
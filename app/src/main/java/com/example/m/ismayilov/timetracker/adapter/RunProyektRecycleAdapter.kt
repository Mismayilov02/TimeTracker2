package com.example.m.ismayilov.timetracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.room.RunHistory
import java.util.*
import java.util.concurrent.TimeUnit

class RunProyektRecycleAdapter(val context: Context, var proyekts: MutableList<RunHistory>  ,var onClickLIstener: OnClickLIstener):RecyclerView.Adapter<RunProyektRecycleAdapter.runProyektViewDesign>() {

    var timeTextList = mutableListOf<TextView>()
    var handlarTrue = true

    inner class runProyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: ImageView
        var name :TextView
        var timeText :TextView

        init {
            color = view.findViewById(R.id.run_proyekt_color)
            name= view.findViewById(R.id.run_proyekt_text)
            timeText= view.findViewById(R.id.run_proyekt_time_text)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): runProyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.proyek_run_design, parent,false)

        return runProyektViewDesign(design)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: runProyektViewDesign, position: Int) {

        val runTime:Long  = (Date().time - proyekts.get(position).start_date)
        val saat = TimeUnit.MILLISECONDS.toHours(proyekts.get(position).daily_total+ runTime) % 24
        val dakika = TimeUnit.MILLISECONDS.toMinutes(proyekts.get(position).daily_total+ runTime) % 60
        val saniye = TimeUnit.MILLISECONDS.toSeconds(proyekts.get(position).daily_total + runTime) % 60

        holder.name.text = proyekts.get(position).project_name
        holder.color.setColorFilter(Color.parseColor(proyekts.get(position).color_code))
        holder.timeText.text = ("${timeToString(saat)}:${timeToString(dakika)}:${timeToString(saniye)}")
        timeTextList.add(holder.timeText)

        while (handlarTrue){
            var handler: Handler = Handler()
            var runnable: Runnable? = null
            var delay = 1000
            handler.postDelayed(Runnable {
                handler.postDelayed(runnable!!, delay.toLong())
                changeTime()

            }.also { runnable = it }, delay.toLong())

            handlarTrue = false
        }
//        holder.btn.setOnClickListener {
//            onClickLIstener.onClickListenerId(proyekts.get(position).my_id)
//        }


    }

    override fun getItemCount(): Int {
        return proyekts.size
    }

    fun timeToString(time:Long):String{
        if(time<10) {
            return "0$time"
        }
       return "$time"
    }

    fun update(proyekts: MutableList<RunHistory>){
        this.proyekts = proyekts
        timeTextList.clear()
        notifyDataSetChanged()
    }


    fun changeTime(){
        for (i in 0..proyekts.size-1){
            var spliteText = timeTextList.get(i).text.split(":" )
            var horse = spliteText[0].toInt()
            var minute = spliteText[1].toInt()
            var second = spliteText[2].toInt()+1

            if(second == 60){
                second = 0
                minute +=1
            }
            if(minute == 60){
                minute = 0
                horse +=1
            }
            timeTextList.get(i).text = ("${timeToString(horse.toLong())}:${timeToString(minute.toLong())}:${timeToString(second.toLong())}")
        }
    }

}
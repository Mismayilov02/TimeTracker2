package com.example.m.ismayilov.timetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.RunHistory

class HistoryBaseAdapter(var context: Context, var list: MutableList<String>, var hashMap: HashMap<String , MutableList<RunHistory>> ):RecyclerView.Adapter<HistoryBaseAdapter.proyektViewDesign>() {

    var showList  = mutableListOf<Boolean>()
    var  myRoomDatabase = MyRoomDatabase.getDatabase(context)

    inner class proyektViewDesign(view :View):RecyclerView.ViewHolder(view){

        var date :TextView
        var listView :RecyclerView

        init {
            date= view.findViewById(R.id.history_date)
            listView = view.findViewById(R.id.history_recylerview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): proyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.history_base_design, parent,false)

        return proyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: proyektViewDesign, position: Int) {
        holder.date.text = list.get(position)
            var historyAdapter = HistoryAdapter(context, hashMap.get(list.get(position)))
            holder.listView.setHasFixedSize(true)
            holder.listView.setLayoutManager(GridLayoutManager(context, 1))
            holder.listView.adapter = historyAdapter!!

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase

class AllProyektRecycleAdapter(val context: Context , var katagory: MutableList<Katagory>  ):RecyclerView.Adapter<AllProyektRecycleAdapter.proyektViewDesign>() {

//     var colorLines  =  ArrayList<CardView>()
    lateinit var myRoomDatabase: MyRoomDatabase
    var onItemClick: ((Int) -> Unit)? = null
    inner class proyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: CardView
        var name :TextView
//        var btn : ImageView
        var listView :ListView

        init {
            color = view.findViewById(R.id.katagory_color)
            name= view.findViewById(R.id.katagory_name)
//            btn= view.findViewById(R.id.proyekt_play_btn)
            listView = view.findViewById(R.id.katagory_expend_listview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): proyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.katagory_design , parent,false)

        return proyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: proyektViewDesign, position: Int) {
        holder.name.text = katagory.get(position).katagory_name
        holder.color.setCardBackgroundColor(Color.parseColor(katagory.get(position).color_code))



    }

    override fun getItemCount(): Int {
        return katagory.size
    }

    fun update(katagory: MutableList<Katagory>){
        this.katagory = katagory
        notifyDataSetChanged()
    }

}
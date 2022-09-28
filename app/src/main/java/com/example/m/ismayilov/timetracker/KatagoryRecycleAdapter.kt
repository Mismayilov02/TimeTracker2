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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase

class KatagoryRecycleAdapter(var context: Context, var katagory: MutableList<Katagory>, var hashMap: HashMap<String , MutableList<Katagory>>  ):RecyclerView.Adapter<KatagoryRecycleAdapter.proyektViewDesign>() {

//     var colorLines  =  ArrayList<CardView>()

     var myRoomDatabase =  MyRoomDatabase.getDatabase(context)
    var onItemClick: ((String) -> Unit)? = null
    var showList  = mutableListOf<Boolean>()

    inner class proyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: CardView
        var name :TextView
//        var btn : ImageView
        var listView :RecyclerView
        var imageViewAdd:ImageView
        var show:ImageView

        init {
            color = view.findViewById(R.id.katagory_color)
            name= view.findViewById(R.id.katagory_name)
//            btn= view.findViewById(R.id.proyekt_play_btn)
            listView = view.findViewById(R.id.katagory_recyclerView)
            imageViewAdd = view.findViewById(R.id.katagory_add)
            show = view.findViewById(R.id.katagory_expend)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): proyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.katagory_design , parent,false)

        return proyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: proyektViewDesign, position: Int) {
        showList.add(position , false)
        holder.name.text = katagory.get(position).katagory_name
        holder.color.setCardBackgroundColor(Color.parseColor(katagory.get(position).color_code))
        holder.imageViewAdd.setOnClickListener {
            onItemClick?.invoke(katagory.get(position).katagory_name)
        }
        holder.show.setOnClickListener{
           if(showList.get(position)){
               showList.set(position , false)
               holder.listView.isVisible = false
               holder.show.setImageResource(R.drawable.angle_small_down)
           }else{
               showList.set(position , true)
               holder.listView.isVisible = true
               holder.show.setImageResource(R.drawable.angle_small_up)
           }
        }

        if(hashMap.get(katagory.get(position).katagory_name)!!.size !=0) {
            var proyektRecycleAdapter = ProyektRecycleAdapter(context, hashMap.get(katagory.get(position).katagory_name))
            holder.listView.setHasFixedSize(true)
            holder.listView.setLayoutManager(GridLayoutManager(context, 1))
            holder.listView.adapter = proyektRecycleAdapter!!
        }

    }

    override fun getItemCount(): Int {
        return katagory.size
    }

    fun update(katagory: MutableList<Katagory>){
        this.katagory = katagory
        notifyDataSetChanged()
    }
}
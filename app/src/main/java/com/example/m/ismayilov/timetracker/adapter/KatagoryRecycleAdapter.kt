package com.example.m.ismayilov.timetracker.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase

class KatagoryRecycleAdapter(var context: Context, var katagory: MutableList<Katagory>, var hashMap: HashMap<String , MutableList<Katagory>>  ,val onClickLIstener: OnClickLIstener):RecyclerView.Adapter<KatagoryRecycleAdapter.proyektViewDesign>() {

    var showList  = mutableListOf<Boolean>()
    var  myRoomDatabase = MyRoomDatabase.getDatabase(context)

    inner class proyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: CardView
        var name :TextView
        var listView :RecyclerView
        var imageViewAdd:ImageView
        var show:ImageView

        init {
            color = view.findViewById(R.id.katagory_color)
            name= view.findViewById(R.id.katagory_name)
            listView = view.findViewById(R.id.katagory_recylerview)
            imageViewAdd = view.findViewById(R.id.katagory_esign_edit)
            show = view.findViewById(R.id.katagory_expend)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): proyektViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.katagory_design, parent,false)

        return proyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: proyektViewDesign, position: Int) {
        showList.add(position , katagory.get(position).expend)
        holder.name.text = katagory.get(position).katagory_name
        holder.color.setCardBackgroundColor(Color.parseColor(katagory.get(position).color_code))
        if (katagory.get(position).expend) {
            holder.listView.isVisible =true
            holder.show.setImageResource(R.drawable.up)
        }

        holder.imageViewAdd.setOnClickListener {
            onClickLIstener.onClickListenerAction(katagory.get(position).katagory_name)
        }

        holder.show.setOnClickListener{
            setVisibleList(holder , position)
        }

        if(hashMap.get(katagory.get(position).katagory_name)!!.size !=0) {
            var proyektRecycleAdapter = ProyektRecycleAdapter(context, hashMap.get(katagory.get(position).katagory_name) , onClickLIstener)
            holder.listView.setHasFixedSize(true)
            holder.listView.setLayoutManager(GridLayoutManager(context, 1))
            holder.listView.adapter = proyektRecycleAdapter!!
        }



    }

    override fun getItemCount(): Int {
        return katagory.size
    }


    fun setVisibleList(holder: proyektViewDesign, position: Int){
        if(showList.get(position) == true){
            showList.set(position , false)
            holder.listView.isVisible = false
            holder.show.setImageResource(R.drawable.angle_down)

        }else{
            showList.set(position , true)
            holder.listView.isVisible = true
            holder.show.setImageResource(R.drawable.up)
        }
        onClickLIstener.onClickSetExpendValues(katagory.get(position).id , showList.get(position))
    }

    fun update(katagory: MutableList<Katagory> ,hashMap: HashMap<String , MutableList<Katagory>>  ){
        this.katagory = katagory
        this.hashMap = hashMap
        notifyDataSetChanged()
    }
}
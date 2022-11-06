package com.example.m.ismayilov.timetracker.adapter

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.SharedPreferencesManager
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase

class KatagoryRecycleAdapter(var context: Context, var katagory: MutableList<Katagory>, var hashMap: HashMap<String , MutableList<Katagory>>  ,val onClickLIstener: OnClickLIstener):RecyclerView.Adapter<KatagoryRecycleAdapter.proyektViewDesign>() {
    var showList  = mutableListOf<Boolean>()
    var adapterList = mutableListOf<ProyektRecycleAdapter>()
    val sharedPreferencesManager = SharedPreferencesManager(context)
    inner class proyektViewDesign(view :View):RecyclerView.ViewHolder(view){
        var color: CardView
        var name :TextView
        var listView :RecyclerView
        var save : Button
        var imageViewAdd:ImageView
        var show:ImageView
        var delete:ImageView
        var edit:ImageView
        var add:ImageView

        init {
            color = view.findViewById(R.id.katagory_color)
            name= view.findViewById(R.id.katagory_name)
            listView = view.findViewById(R.id.katagory_recylerview)
            save = view.findViewById(R.id.katagory_design_save_btn)
            imageViewAdd = view.findViewById(R.id.katagory_esign_edit)
            show = view.findViewById(R.id.katagory_expend)
            edit = view.findViewById(R.id.katagory_edit)
            delete = view.findViewById(R.id.katagory_delete)
            add = view.findViewById(R.id.katagory_add)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): proyektViewDesign {
        val design  = LayoutInflater.from(context).inflate(R.layout.katagory_design, parent,false)

        return proyektViewDesign(design)
    }

    override fun onBindViewHolder(holder: proyektViewDesign, position: Int) {
        showList.add(position, katagory.get(position).expend)
        var proyektRecycleAdapter = ProyektRecycleAdapter(context, hashMap.get(katagory.get(position).katagory_name) , onClickLIstener)
        holder.listView.setHasFixedSize(true)
        holder.listView.setLayoutManager(GridLayoutManager(context, 1))
        holder.listView.adapter = proyektRecycleAdapter!!
        adapterList.add(proyektRecycleAdapter)

        holder.name.text = katagory.get(position).katagory_name
        holder.color.setCardBackgroundColor(Color.parseColor(katagory.get(position).color_code))

        if (katagory.get(position).katagory_name.equals("NSP")){
            holder.imageViewAdd.isVisible = false
        }
        if (katagory.get(position).expend) {
            holder.listView.isVisible =true
            holder.show.setImageResource(R.drawable.up)
        }

        holder.show.setOnClickListener{
            setVisibleList(holder , position , false)
        }

        holder.imageViewAdd.setOnClickListener {
            editVisible(true , holder , position)
        }

        holder.save.setOnClickListener {
            editVisible(false , holder , position)
        }

        holder.edit.setOnClickListener {
            onClickLIstener.onClickSetEditName(katagory.get(position).project_name , katagory.get(position).katagory_name)
        }

        holder.add.setOnClickListener {
            onClickLIstener.onClickListenerAction(katagory.get(position).katagory_name)
        }

        holder.delete.setOnClickListener {
            onClickLIstener.onClickSetDelete(katagory.get(position).katagory_name , katagory.get(position).project_name)
        }



    }

    override fun getItemCount(): Int {
        return katagory.size
    }


    fun setVisibleList(holder: proyektViewDesign, position: Int , edit:Boolean){
        if(showList.get(position) == true && !edit){
            showList.set(position , false)
            holder.listView.isVisible = false
            holder.show.setImageResource(R.drawable.angle_down)
            if(katagory.get(position).id == 50){ sharedPreferencesManager.setValue("NSPExpend", false) }

        }else{
            if (hashMap.get(katagory.get(position).katagory_name) != null){

                holder.listView.isVisible = true
            }
            showList.set(position, true)
            holder.show.setImageResource(R.drawable.up)
            if(katagory.get(position).id == 50){ sharedPreferencesManager.setValue("NSPExpend", true) }
        }
        onClickLIstener.onClickSetExpendValues(katagory.get(position).id.toString() , showList.get(position))
    }


    fun editVisible(visibility:Boolean , holder: proyektViewDesign , position: Int){
        if (visibility){
            holder.edit.visibility = View.VISIBLE
            holder.delete.visibility = View.VISIBLE
            holder.add.visibility = View.VISIBLE
            holder.show.visibility = View.INVISIBLE
            holder.imageViewAdd.visibility = View.INVISIBLE
            holder.save.visibility = View.VISIBLE
            setVisibleList(holder , position , true)
            Handler(Looper.getMainLooper()).postDelayed({
                adapterList.get(position).editVisible(true)
            }, 5)

        }else{
            holder.edit.visibility = View.GONE
            holder.delete.visibility = View.GONE
            holder.add.visibility = View.GONE
            holder.show.visibility = View.VISIBLE
            holder.imageViewAdd.visibility = View.VISIBLE
            holder.save.visibility = View.GONE
            adapterList.get(position).editVisible(false)
        }
    }

}
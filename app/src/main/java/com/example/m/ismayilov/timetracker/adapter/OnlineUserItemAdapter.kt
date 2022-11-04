package com.example.m.ismayilov.timetracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.OnlineAdapter
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.R
import com.example.m.ismayilov.timetracker.UserDeaultProjectName
import com.example.m.ismayilov.timetracker.onClick.OnlineOnClickLIstener
import com.example.m.ismayilov.timetracker.room.Katagory

class OnlineUserItemAdapter(val context: Context, var phone:String ,var list: MutableList<UserDeaultProjectName> , var onlineOnClickLIstener: OnlineOnClickLIstener):RecyclerView.Adapter<OnlineUserItemAdapter.cardViewDesign>() {

    var projeList = mutableListOf<cardViewDesign>()

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var baseColor: ImageView
        var edit: ImageView
        var delete: ImageView
        var textView :TextView


        init {
            baseColor = view.findViewById(R.id.online_proyekt_color)
            edit = view.findViewById(R.id.online_project_edit)
            delete = view.findViewById(R.id.online_project_delete)
            textView= view.findViewById(R.id.online_proyekt_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.online_proyek_design, parent,false)

        return cardViewDesign(design)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        projeList.add(holder)
        holder.baseColor.setColorFilter(Color.parseColor(list.get(position).colorCode))
        holder.textView.text = list.get(position).projectName

        holder.edit.setOnClickListener {
            onlineOnClickLIstener.onClickSetUserChangePojectName(phone , list.get(position).projectName)
        }

        holder.delete.setOnClickListener {
            onlineOnClickLIstener.onClickSetDeleteUserProject(phone , list.get(position).projectName)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun editOnClick(editShow:Boolean  ){
        for (i in projeList){
            if (editShow) {
                i.delete.visibility = View.VISIBLE
                i.edit.visibility = View.VISIBLE
            } else {
                i.delete.visibility = View.GONE
                i.edit.visibility = View.GONE
            }
        }

    }

}
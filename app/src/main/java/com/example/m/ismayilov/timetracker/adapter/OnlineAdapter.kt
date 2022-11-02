package com.example.m.ismayilov.timetracker

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.adapter.OnlineUserItemAdapter
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener

class OnlineAdapter(val context: Context, var onClickLIstener: OnClickLIstener, val list: MutableList<Users>,  /*val userProyekt: HashMap<String , MutableList<UserDefaultProject>>*/ ):RecyclerView.Adapter<OnlineAdapter.cardViewDesign>() {

    var showList  = mutableListOf<Boolean>()
    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var name: TextView
        var phone :TextView
        var profile:ImageView
        var edit:ImageView
        var expand:ImageView
        var history:ImageView
        var lisview:RecyclerView


        init {
            name = view.findViewById(R.id.online_name_text)
            phone= view.findViewById(R.id.online_phone)
            profile= view.findViewById(R.id.online_profile)
            expand= view.findViewById(R.id.online_expend)
            history= view.findViewById(R.id.online_user_history)
            edit= view.findViewById(R.id.online_edit)
            lisview= view.findViewById(R.id.online_user_recyclerview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewDesign {
       val design  = LayoutInflater.from(context).inflate(R.layout.online_recycler_design , parent,false)

        return cardViewDesign(design)
    }

    override fun onBindViewHolder(holder: cardViewDesign, position: Int) {
        showList.add(position , false)
        if(list.get(position).online)
            holder.profile.setColorFilter(ContextCompat.getColor(context, R.color.green))

        holder.name.text = list.get(position).name
        holder.phone.text = list.get(position).phone


        if (list.get(position).project != null){
            val projeList = mutableListOf<UserDeaultProjectName>()
            for ((key, value) in list.get(position).project!!) {
                projeList.add(UserDeaultProjectName(key, value.colorCode))
            }
            val adapter = OnlineUserItemAdapter(context, projeList)
            holder.lisview.setHasFixedSize(true)
            holder.lisview.setLayoutManager(GridLayoutManager(context, 1))
            holder.lisview.adapter = adapter
        }

        holder.expand.setOnClickListener {
            setVisibleList(holder , position)
        }

        holder.edit.setOnClickListener {
            onClickLIstener.onClickListenerAction(holder.phone.text.toString())
        }
        holder.history.setOnClickListener {
            onClickLIstener.onClickSetExpendValues(list.get(position).phone , false)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    fun setVisibleList(holder: OnlineAdapter.cardViewDesign, position: Int){
        if(showList.get(position) == true){
            showList.set(position , false)
            holder.lisview.visibility = View.GONE
            holder.expand.setImageResource(R.drawable.angle_down)

        }else{
            showList.set(position , true)
            holder.lisview.visibility = View.VISIBLE
            holder.expand.setImageResource(R.drawable.up)
        }
    }


}
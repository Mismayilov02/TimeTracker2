package com.example.m.ismayilov.timetracker

import android.content.Context
import android.opengl.Visibility
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m.ismayilov.timetracker.adapter.OnlineUserItemAdapter
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.onClick.OnlineOnClickLIstener

class OnlineAdapter(val context: Context, var onClickLIstener: OnlineOnClickLIstener, val list: MutableList<Users>):RecyclerView.Adapter<OnlineAdapter.cardViewDesign>() {

    var showList  = mutableListOf<Boolean>()
    var adapterList = mutableListOf<OnlineUserItemAdapter>()
    lateinit var Useradapter:OnlineUserItemAdapter

    inner class cardViewDesign(view :View):RecyclerView.ViewHolder(view){
        var name: TextView
        var phone :TextView
        var profile:ImageView
        var edit:ImageView
        var expand:ImageView
        var delete:ImageView
        var add:ImageView
        var userEdit:ImageView
        var save:Button
        var lisview:RecyclerView


        init {
            name = view.findViewById(R.id.online_name_text)
            phone= view.findViewById(R.id.online_phone)
            profile= view.findViewById(R.id.online_profile)
            expand= view.findViewById(R.id.online_expend)
            userEdit= view.findViewById(R.id.online_user_edit)
            save= view.findViewById(R.id.online_user_save)
            delete= view.findViewById(R.id.online_user_delete)
            add= view.findViewById(R.id.online_user_add)
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

        var projeList = mutableListOf<UserDeaultProjectName>()
        if (list.get(position).project != null) {
            for ((key, value) in list.get(position).project!!) {
                projeList.add(UserDeaultProjectName(key, value.colorCode))
            }
        }
            Useradapter = OnlineUserItemAdapter(context, list.get(position).phone , projeList ,onClickLIstener)
            holder.lisview.setHasFixedSize(true)
            holder.lisview.setLayoutManager(GridLayoutManager(context, 1))
            holder.lisview.adapter = Useradapter
            adapterList.add(Useradapter)
//        }


        holder.expand.setOnClickListener {
            setVisibleList(holder , position , false)
        }

        holder.edit.setOnClickListener {
            editOnClick(true , position , holder)
        }

//        holder.userEdit.setOnClickListener {
//            onClickLIstener.onClickSetUserChangePojectName(list.get(position).phone)
//        }

        holder.save.setOnClickListener {
            editOnClick(false , position , holder)
        }
        holder.add.setOnClickListener {
            onClickLIstener.onClickListenerAddProject(holder.phone.text.toString())
        }
        holder.profile.setOnClickListener {
            onClickLIstener.onClickSetHistory(list.get(position).phone)
        }
        holder.name.setOnClickListener {
            onClickLIstener.onClickSetHistory(list.get(position).phone)
        }
        holder.phone.setOnClickListener {
            onClickLIstener.onClickSetHistory(list.get(position).phone)
        }
        holder.delete.setOnClickListener {
            onClickLIstener.onClickSetDeleteUser(list.get(position).phone)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    fun setVisibleList(holder: OnlineAdapter.cardViewDesign, position: Int , expend:Boolean){
        if(showList.get(position) == true && !expend ){
            showList.set(position , false)
            holder.lisview.visibility = View.GONE
            holder.expand.setImageResource(R.drawable.angle_down)

        }else{
            showList.set(position , true)
            holder.lisview.visibility = View.VISIBLE
            holder.expand.setImageResource(R.drawable.up)
        }
    }


    fun editOnClick(editShow:Boolean , position: Int, holder: cardViewDesign){
        if (editShow){
            holder.userEdit.visibility = View.VISIBLE
            holder.delete.visibility = View.VISIBLE
            holder.add.visibility = View.VISIBLE
            holder.save.visibility = View.VISIBLE
            holder.edit.visibility = View.GONE
            holder.expand.visibility = View.GONE
            setVisibleList(holder , position , true)

            Handler(Looper.getMainLooper()).postDelayed({
                adapterList.get(position).editOnClick(true)
            }, 5)
        }else{
            holder.userEdit.visibility = View.GONE
            holder.delete.visibility = View.GONE
            holder.add.visibility = View.GONE
            holder.save.visibility = View.GONE
            holder.edit.visibility = View.VISIBLE
            holder.expand.visibility = View.VISIBLE
            adapterList.get(position).editOnClick(false)
        }

    }

}
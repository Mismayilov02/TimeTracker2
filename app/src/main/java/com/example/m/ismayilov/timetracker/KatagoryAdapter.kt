package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.m.ismayilov.timetracker.room.Katagory

class KatagoryAdapter(var context: Activity , var katagorys: MutableList<Katagory>):ArrayAdapter<Katagory>(context , R.layout.proyek_design ,R.id.proyekt_text ,katagorys) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view :View = LayoutInflater.from(context).inflate(R.layout.proyek_design,null)

        val cardView:CardView = view.findViewById(R.id.proyekt_color)
        val textView:TextView = view.findViewById(R.id.proyekt_text)

        cardView.setCardBackgroundColor(Color.parseColor(katagorys.get(position).color_code))
        textView.text = katagorys.get(position).katagory_name
//        println(katagorys.get(position).katagory_name)


        return view
    }
}
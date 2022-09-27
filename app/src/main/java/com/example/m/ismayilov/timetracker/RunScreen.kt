package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.databinding.FragmentRunScreenBinding
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.ProjektHistory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Date

class RunScreen : Fragment() {
   var view: FrameLayout? = null
    lateinit var binding: FragmentRunScreenBinding
    lateinit var katagoryAdapter: KatagoryAdapter
    lateinit var allProyektRecycleAdapter: AllProyektRecycleAdapter
    lateinit var runProyektRecycleAdapter: RunProyektRecycleAdapter
    lateinit var katagory: MutableList<Katagory>
    lateinit var katagoryHistory: MutableList<Katagory>
    lateinit var runKatagoryHistory: MutableList<Katagory>
    lateinit var myRoomDatabase: MyRoomDatabase
    @RequiresApi(Build.VERSION_CODES.N)
    val simpleToDay  = SimpleDateFormat("yyyy-MM-dd")
    var katagoryname: String =""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRunScreenBinding.inflate(inflater, container, false)
        view = binding.root

        myRoomDatabase = MyRoomDatabase.getDatabase(requireContext())
        sharedPreferences =  requireContext().getSharedPreferences("user" , Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        lifecycleScope.launch {
            katagory = myRoomDatabase.katagoryDao().read_null_katagory("null")
            allProyektRecycleAdapter = AllProyektRecycleAdapter(requireContext() , katagory)
            binding.runAllProyekt.setHasFixedSize(true)
            binding.runAllProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runAllProyekt.adapter = allProyektRecycleAdapter
//            katagoryAdapter = KatagoryAdapter(requireActivity() , katagory)
//            binding.spinner.adapter = katagoryAdapter
//
//            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View,
//                    position: Int,
//                    id: Long
//                ) {
//                    katagoryname = katagory.get(position).katagory_name
//                    setAllProyekt(katagoryname)
//                    setRunProyekt(katagoryname)
//                } // to close the onItemSelected
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                }
//            }
            toDateCheck()
        }


        binding.runAddKatagory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.navigation_run_toadd)
//            allProyektRecycleAdapter.notifyDataSetChanged()
        }
//        binding.runAddProyek.setOnClickListener {
//            if (!katagoryname.equals("")){
//                val direction = RunScreenDirections.navigationRunToadd(true, katagoryname)
//                 Navigation.findNavController(it).navigate(direction)
//            }else
//            {
//                Snackbar.make(it , "lutfen bir katagory seciniz" , Snackbar.LENGTH_SHORT).show()
//            }
//        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllProyekt(katagoryType:String){
        lifecycleScope.launch{
            katagoryHistory =myRoomDatabase.katagoryDao().read_notNull_katagory(false)
            allProyektRecycleAdapter = AllProyektRecycleAdapter(requireContext() ,katagoryHistory )
            binding.runAllProyekt.setHasFixedSize(true)
            binding.runAllProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runAllProyekt.adapter = allProyektRecycleAdapter
            allProyektRecycleAdapter.onItemClick = {
                lifecycleScope.launch{
                    notfyChangedAdapter(it , false)
                }
            }
        }
    }

    fun setRunProyekt(katagoryType:String){
        lifecycleScope.launch{
            runKatagoryHistory = myRoomDatabase.katagoryDao().read_notNull_katagory(true)
            runProyektRecycleAdapter = RunProyektRecycleAdapter(requireContext() ,runKatagoryHistory )
            binding.runRunProyekt.setHasFixedSize(true)
            binding.runRunProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runRunProyekt.adapter = runProyektRecycleAdapter
            runProyektRecycleAdapter.onItemClick = {
            notfyChangedAdapter(it , true)

            }
        }
    }

    fun notfyChangedAdapter( it:Int, run:Boolean){
        lifecycleScope.launch{
            if (run){
                myRoomDatabase.projectDao().update_history_play(it, false)
            }
            else{
                myRoomDatabase.projectDao().update_history_play(it, true)
            }

            runKatagoryHistory = myRoomDatabase.katagoryDao().read_notNull_katagory(true)
            katagoryHistory = myRoomDatabase.katagoryDao().read_notNull_katagory(false)
            allProyektRecycleAdapter.update(katagoryHistory)
            runProyektRecycleAdapter.update(runKatagoryHistory)
        }
    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun daelyCreateProyekt(date: Date){
//        lifecycleScope.launch {
//          val dalyProyekt = myRoomDatabase.projectDao().read_distinc_katagory()
//            for (i in dalyProyekt){
//                val tprojektHistory = ProjektHistory(0,i.color_code , i.katagory_name ,i.project_name , 0, 0,
//                    0,simpleToDay.format(date.time) ,false)
//                myRoomDatabase.projectDao().write_katagoryr(tprojektHistory)
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toDateCheck(){
        val date = Date()
        if(!sharedPreferences.getString("todate" , "aegasgeg").equals(simpleToDay.format(date.time))){
            editor.putString("todate" , simpleToDay.format(date.time))
//            daelyCreateProyekt(date )
        }
    }
}
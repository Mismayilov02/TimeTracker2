package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.adapter.KatagoryRecycleAdapter
import com.example.m.ismayilov.timetracker.adapter.RunProyektRecycleAdapter
import com.example.m.ismayilov.timetracker.databinding.FragmentRunScreenBinding
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.RunHistory
import kotlinx.coroutines.launch
import java.util.*


class RunScreen : Fragment() , OnClickLIstener {
   var view: FrameLayout? = null
    lateinit var binding: FragmentRunScreenBinding
    lateinit var katagoryRecycleAdapter: KatagoryRecycleAdapter
    lateinit var runProyektRecycleAdapter: RunProyektRecycleAdapter
    lateinit var katagory: MutableList<Katagory>
    lateinit var runKatagoryHistory: MutableList<RunHistory>
    lateinit var myRoomDatabase: MyRoomDatabase
    var hashMap = HashMap<String , MutableList<Katagory>>()
    @RequiresApi(Build.VERSION_CODES.N)
    val simpleToDay  = SimpleDateFormat("yyyy-MM-dd")
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    @SuppressLint("SuspiciousIndentation")
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

        toDateCheck()
        setAdapterDefault()
        setAdapterRun()


        binding.runscreenEmptyText.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_runScreen2_to_addProek2)
        }


        binding.runAddKatagory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_runScreen2_to_addProek2)
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toDateCheck(){
        if(!sharedPreferences.getString("todate" , "1111.11.11").equals(simpleToDay.format(Date().time))){
            editor.putString("todate" , simpleToDay.format(Date().time)).apply()
            editor.commit()
        }
    }

    fun updateRun(id:Int , play: Boolean) {
        lifecycleScope.launch {
            if(myRoomDatabase.runDao().readAllKatagory(true).size <= 2 || play) {
                val run = myRoomDatabase.katagoryDao().readId(id)
                myRoomDatabase.katagoryDao().updateRun(id, !run.run)
                katagory = myRoomDatabase.katagoryDao().readNullKatagory("null")
                createrunProject(run)
                for (i in katagory) {
                    val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name)
                    hashMap.put(i.katagory_name, hasmapKatagory)
                }
                katagoryRecycleAdapter.update(katagory, hashMap)
            }else{
                ArtelDialog().getMaxDialog(requireContext())
            }


        }
    }

    fun createrunProject(katagory: Katagory){
        val time  =sharedPreferences.getString("todate" , "1111.11.11")
        lifecycleScope.launch {
            lateinit var runUpdate:RunHistory
            val katagoryRun = myRoomDatabase.runDao().readDalyTrue(katagory.id ,time!! , true)

            if (katagoryRun != null){

                if(katagoryRun.play){
                   val eguals = (Math.abs(Date().time - katagoryRun.start_date)) + katagoryRun.daily_total
                   runUpdate = RunHistory(katagoryRun.id , katagory.id, katagoryRun.color_code ,katagoryRun.katagory_name , katagoryRun.project_name , katagoryRun.start_date ,Date().time,
                        eguals,  katagoryRun.date,false  )
                }
                else{
                    runUpdate = RunHistory(katagoryRun.id , katagory.id ,katagoryRun.color_code ,katagoryRun.katagory_name , katagoryRun.project_name , Date().time ,katagoryRun.end_date,
                        katagoryRun.daily_total, katagoryRun.date ,true  )
                }
                    runUpdate.id = katagoryRun.id
                    myRoomDatabase.runDao().updateRun(runUpdate)

            }else{
                myRoomDatabase.runDao().inertRunHistory(RunHistory(0 , katagory.id,katagory.color_code ,katagory.katagory_name , katagory.project_name , Date().time ,0,
                   0, time!! ,true  ))
            }
            runKatagoryHistory = myRoomDatabase.runDao().readAllKatagory(true)
            runProyektRecycleAdapter.update(runKatagoryHistory)
            setDesignRunIconView(runKatagoryHistory.size)

        }

    }

    fun setAdapterDefault(){
        lifecycleScope.launch {
            katagory = myRoomDatabase.katagoryDao().readNullKatagory("null")
            if(katagory.size!=0){
                binding.runscreenEmptyText.isVisible = false
            }
            for(i in katagory){
                val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name )
                hashMap.put(i.katagory_name ,hasmapKatagory )
            }
            katagoryRecycleAdapter = KatagoryRecycleAdapter(requireContext() , katagory ,hashMap , this@RunScreen)
            binding.runAllProyekt.setHasFixedSize(true)
            binding.runAllProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runAllProyekt.adapter = katagoryRecycleAdapter
        }
    }

    fun setAdapterRun(){
        lifecycleScope.launch {
            runKatagoryHistory = myRoomDatabase.runDao().readAllKatagory(true)
            setDesignRunIconView(runKatagoryHistory.size)
            runProyektRecycleAdapter = RunProyektRecycleAdapter(requireContext() ,runKatagoryHistory  , this@RunScreen)
            binding.runRunProyekt.setHasFixedSize(true)
            binding.runRunProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runRunProyekt.adapter = runProyektRecycleAdapter
//            changeTime()
        }
    }

    fun setExpendValues(id: Int, expend: Boolean){
        lifecycleScope.launch {
           myRoomDatabase.katagoryDao().updateExpendValues(id , expend)
        }
    }

    override fun onClickListenerId(id: Int  , play: Boolean) {
        updateRun(id , play)
    }

    override fun onClickListenerAction(katagoryName: String) {
        val direction = RunScreenDirections.actionRunScreen2ToAddProek2(true, katagoryName)
        Navigation.findNavController(requireView()).navigate(direction)
    }

    override fun onClickSetExpendValues(id: Int, expend: Boolean) {
        setExpendValues(id , expend)
    }

    fun setDesignRunIconView(size:Int){
        if(size== 0){
            binding.runscreenClock.isVisible  = false
            binding.runscreenPlay.isVisible = false
        }
        else{
            binding.runscreenClock.isVisible  = true
            binding.runscreenPlay.isVisible = true
        }

    }

}
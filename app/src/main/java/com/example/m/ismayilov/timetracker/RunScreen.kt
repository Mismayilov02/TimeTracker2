package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.adapter.KatagoryRecycleAdapter
import com.example.m.ismayilov.timetracker.adapter.RunProyektRecycleAdapter
import com.example.m.ismayilov.timetracker.databinding.FragmentRunScreenBinding
import com.example.m.ismayilov.timetracker.onClick.OnClickLIstener
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.RunHistory
import com.example.m.ismayilov.timetracker.unkonown.ArtelDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.util.*


class RunScreen : Fragment() , OnClickLIstener {
   var view: FrameLayout? = null
    private val serverKey = "BDzjOJCrzrb1AlylmsS4p6HmZmA7EQbVOQFqbZSNvZp0UPafDGP8ya5nUBO29FMgvC8VzqE6VBy8wbnrGvO9z5M"
    lateinit var binding: FragmentRunScreenBinding
    lateinit var katagoryRecycleAdapter: KatagoryRecycleAdapter
    lateinit var runProyektRecycleAdapter: RunProyektRecycleAdapter
    lateinit var katagoryList: MutableList<Katagory>
    lateinit var runKatagoryHistoryList: MutableList<RunHistory>
    var katagoryRun :RunHistory? = null
    lateinit var time: String
    var updateRun = false
    var updateKatagory = false
    lateinit var myRoomDatabase: MyRoomDatabase
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    var hashMap = HashMap<String , MutableList<Katagory>>()
    @RequiresApi(Build.VERSION_CODES.N)
    lateinit var sharedPreferencesManager: SharedPreferencesManager



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
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")
//        FireBaseSendMessage.SetServerKey(serverKey)
        setAdapterDefault()
        setAdapterRun()


        binding.runscreenEmptyText.setOnClickListener {
            findNavController().navigate(R.id.action_runScreen2_to_addProek2)
        }


        binding.runAddKatagory.setOnClickListener {
            findNavController().navigate(R.id.action_runScreen2_to_addProek2)
        }

        return view
    }


    @SuppressLint("NewApi")
    fun updateRun(id:Int, play: Boolean) {
        lifecycleScope.launch {
            if(myRoomDatabase.runDao().readAllKatagory(true).size <= 2 || play) {
                val run = myRoomDatabase.katagoryDao().readId(id)
                myRoomDatabase.katagoryDao().updateRun(id, !run.run)
                katagoryList = myRoomDatabase.katagoryDao().readNullKatagory("nulll")
                createRunProject(run)
                for (i in katagoryList) {
                    val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name)
                    hashMap.put(i.katagory_name, hasmapKatagory)
                }
                setAdapterRunOrUpdate(katagoryList, hashMap)
            }else{
                ArtelDialog().getMaxDialog(requireContext())
            }

        }
    }


    @SuppressLint("NewApi")
    fun createRunProject(katagory: Katagory){
        time = sharedPreferencesManager.getString("todate" , "1111.11.11")!!
        lifecycleScope.launch {
            katagoryRun = myRoomDatabase.runDao().readDalyTrue(katagory.katagory_name , katagory.project_name )

            if (katagoryRun != null){
                if(time.equals(katagoryRun!!.date)){
                    katagoryRunUpdatePause(katagoryRun!!.play, false, katagory)
                }else {
                    if (katagoryRun!!.play){
                        katagoryRunUpdatePause(true, false, katagory)
                    }
                    else{
                        katagoryRunUpdatePause(false , true , katagory)
                    }
                }

            }else{
                katagoryRunUpdatePause(false , true , katagory)
            }

            runKatagoryHistoryList = myRoomDatabase.runDao().readAllKatagory(true)
            setRunAdatpterOrUpdate(runKatagoryHistoryList)
            setDesignRunIconView(runKatagoryHistoryList.size)
        }

    }

    suspend fun katagoryRunUpdatePause(play: Boolean, create:Boolean, katagory: Katagory){
        lateinit var runUpdate:RunHistory
        if(create){
            myRoomDatabase.runDao().inertRunHistory(RunHistory(0 , katagory.id,katagory.color_code ,katagory.katagory_name , katagory.project_name , Date().time ,0,
                0, time!! ,true  ))
        }else{
            if(play){
                val eguals = (Math.abs(Date().time - katagoryRun!!.start_date)) + katagoryRun!!.daily_total
                runUpdate = RunHistory(katagoryRun!!.id , katagory.id, katagoryRun!!.color_code ,katagoryRun!!.katagory_name , katagoryRun!!.project_name , katagoryRun!!.start_date ,Date().time,
                    eguals,  katagoryRun!!.date,false  )
            }else{
                runUpdate = RunHistory(katagoryRun!!.id , katagory.id ,katagoryRun!!.color_code ,katagoryRun!!.katagory_name , katagoryRun!!.project_name , Date().time ,katagoryRun!!.end_date,
                    katagoryRun!!.daily_total, katagoryRun!!.date ,true  )
            }
            runUpdate.id = katagoryRun!!.id
            myRoomDatabase.runDao().updateRun(runUpdate)
        }

    }

    fun setRunAdatpterOrUpdate(list:MutableList<RunHistory>){
        if (!updateRun){
            runProyektRecycleAdapter = RunProyektRecycleAdapter(requireContext() ,list  , this@RunScreen)
            binding.runRunProyekt.setHasFixedSize(true)
            binding.runRunProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runRunProyekt.adapter = runProyektRecycleAdapter
            updateRun = true
        }else{
            runProyektRecycleAdapter.update(list)
        }
    }
    fun setAdapterRunOrUpdate(katagory: MutableList<Katagory>  , hashMap:HashMap<String , MutableList<Katagory>>){
        if (!updateKatagory){
            katagoryRecycleAdapter = KatagoryRecycleAdapter(requireContext() , katagory ,hashMap , this@RunScreen)
            binding.runAllProyekt.setHasFixedSize(true)
            binding.runAllProyekt.setLayoutManager(GridLayoutManager(activity, 1))
            binding.runAllProyekt.adapter = katagoryRecycleAdapter
            updateKatagory = true
        }else{
            katagoryRecycleAdapter.update(katagory , hashMap)
        }
    }


    fun setAdapterDefault() {
        lifecycleScope.launch {
            katagoryList = myRoomDatabase.katagoryDao().readNullKatagory("nulll")
            binding.runscreenEmptyText.isVisible = katagoryList.size == 0
            for (i in katagoryList) {
                val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name)
                hashMap.put(i.katagory_name, hasmapKatagory)
            }
          setAdapterRunOrUpdate(katagoryList , hashMap)
        }
    }
    fun setAdapterRun(){
        lifecycleScope.launch {
            runKatagoryHistoryList = myRoomDatabase.runDao().readAllKatagory(true)
            setDesignRunIconView(runKatagoryHistoryList.size)
            var list = mutableListOf<RunHistory>()
            for (i in runKatagoryHistoryList){
                if (myRoomDatabase.katagoryDao().readCheckKatagory(i.katagory_name , i.project_name) != null){
                    list.add(i)
                }else{
                    i.play = false
                    myRoomDatabase.runDao().updateRun(i)
                }
            }
           setRunAdatpterOrUpdate(list)
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
        val direction = RunScreenDirections.actionRunScreen2ToAddProek2( katagoryName , "" , "project")
        Navigation.findNavController(requireView()).navigate(direction)
    }

    override fun onClickSetExpendValues(id: String, expend: Boolean) {
        setExpendValues(id.toInt() , expend)
    }

    override fun onClickSetDelete(katagoryName: String, projectName: String) {
      getDeleteProjectDialog( katagoryName , projectName)
    }

    override fun onClickSetEditName(projectName: String, katagoryName: String) {
        val direction = RunScreenDirections.actionRunScreen2ToAddProek2(katagoryName , "" , "changeName" , projectName)
        Navigation.findNavController(requireView()).navigate(direction)
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

    fun getDeleteProjectDialog( katagory:String, project:String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.delete_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.findViewById<ImageView>(R.id.delete_dialog_exit).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.delete_dialog_yes).setOnClickListener {
                lifecycleScope.launch {
                    if (project.equals("nulll")){
                            myRoomDatabase.katagoryDao().deleteKatagory(katagory)
                    }else{
                        myRoomDatabase.katagoryDao().deleteProject(katagory , project)
                    }
                    setAdapterRun()
                    setAdapterDefault()
                    dialog.dismiss()
                }

        }



    }

}
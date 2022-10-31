package com.example.m.ismayilov.timetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import java.util.*


class RunScreen : Fragment() , OnClickLIstener {
   var view: FrameLayout? = null
    private val serverKey = "BDzjOJCrzrb1AlylmsS4p6HmZmA7EQbVOQFqbZSNvZp0UPafDGP8ya5nUBO29FMgvC8VzqE6VBy8wbnrGvO9z5M"
    lateinit var binding: FragmentRunScreenBinding
    var katagoryRun :RunHistory? = null
    lateinit var katagoryRecycleAdapter: KatagoryRecycleAdapter
    lateinit var runProyektRecycleAdapter: RunProyektRecycleAdapter
     var katagory = mutableListOf<Katagory>()
    lateinit var runKatagoryHistory: MutableList<RunHistory>
    lateinit var myRoomDatabase: MyRoomDatabase
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var time: String
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
        firebaseGetProject()

        setAdapterRun()


        binding.runAddKatagory.setOnClickListener {
            findNavController().navigate(R.id.action_runScreen2_to_addProek2)
        }

        return view
    }


    @SuppressLint("NewApi")
    fun updateRun(katagory: Katagory) {
        lifecycleScope.launch {
            if(myRoomDatabase.runDao().readAllKatagory(true).size <= 2 || katagory.run) {
                val run = myRoomDatabase.katagoryDao().readId(katagory.id)
               if(katagory.id != 50) {
                   myRoomDatabase.katagoryDao().updateRun(katagory.id, !run.run)
                   createrunProject(run)
               }else{

                   firebaseUpdatePlay(katagory)
                   createrunProject(Katagory(50 , katagory.color_code , "NSP" ,katagory.project_name
                       , katagory.run , false ))
               }
//                katagory = myRoomDatabase.katagoryDao().readNullKatagory("null")

//                for (i in katagory) {
//                    val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name)
//                    hashMap.put(i.katagory_name, hasmapKatagory)
//                }
//                katagoryRecycleAdapter.update(katagory, hashMap)
            }else{
                ArtelDialog().getMaxDialog(requireContext())
            }


        }
    }


    @SuppressLint("NewApi")
    fun createrunProject(katagory: Katagory){
        time = sharedPreferencesManager.getString("todate" , "1111.11.11")!!
        lifecycleScope.launch {
           katagoryRun = myRoomDatabase.runDao().readDalyTrue(katagory.katagory_name , katagory.project_name )

            if (katagoryRun != null){
                if(time.equals(katagoryRun!!.date)){
                        katagoryRunUpdatePause(katagoryRun!!.play, false, katagory)
                }else {
                    if (katagoryRun!!.play){ katagoryRunUpdatePause(true, false, katagory) }
                    else{
                        katagoryRunUpdatePause(false , true , katagory)
                    }
                }

            }else{
                katagoryRunUpdatePause(false , true , katagory)
            }
            runKatagoryHistory = myRoomDatabase.runDao().readAllKatagory(true)
            runProyektRecycleAdapter.update(runKatagoryHistory)
            setDesignRunIconView(runKatagoryHistory.size)
            checkIsOnline(runKatagoryHistory.size)

        }

    }

    suspend fun katagoryRunUpdatePause(play: Boolean, create:Boolean, katagory: Katagory){
        lateinit var runHistory:RunHistory
        if(create){
              runHistory = RunHistory(0 , katagory.id,katagory.color_code ,katagory.katagory_name , katagory.project_name , Date().time ,0,
                0, time!! ,true  )
            myRoomDatabase.runDao().inertRunHistory(runHistory)

        }else{
            if(play){
                val eguals = (Math.abs(Date().time - katagoryRun!!.start_date)) + katagoryRun!!.daily_total
                runHistory = RunHistory(katagoryRun!!.id , katagory.id, katagoryRun!!.color_code ,katagoryRun!!.katagory_name , katagoryRun!!.project_name , katagoryRun!!.start_date ,Date().time,
                    eguals,  katagoryRun!!.date,false  )
            }else{
                runHistory = RunHistory(katagoryRun!!.id , katagory.id ,katagoryRun!!.color_code ,katagoryRun!!.katagory_name , katagoryRun!!.project_name , Date().time ,katagoryRun!!.end_date,
                    katagoryRun!!.daily_total, katagoryRun!!.date ,true  )
            }
            runHistory.id = katagoryRun!!.id
            myRoomDatabase.runDao().updateRun(runHistory)
        }
        updateFireBaseHistory(runHistory)
    }

    fun setAdapterDefault(){
        lifecycleScope.launch {

            katagory.addAll(myRoomDatabase.katagoryDao().readNullKatagory("null"))

            for(i in katagory){
                if(!i.katagory_name.equals("NSP")) {
                    val hasmapKatagory = myRoomDatabase.katagoryDao().readKatagory(i.katagory_name )
                    hashMap.put(i.katagory_name ,hasmapKatagory )
                }
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
        }
    }

    fun setExpendValues(id: Int, expend: Boolean){
        lifecycleScope.launch {
           myRoomDatabase.katagoryDao().updateExpendValues(id , expend)
        }
    }

    override fun onClickListenerId(katagory: Katagory) {
        updateRun(katagory)
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

    @SuppressLint("NewApi")
    fun firebaseGetProject(){

        var sorgu  = firebase!!.orderByChild("phone").equalTo(sharedPreferencesManager.getString("phone" , "defaulUserPhone"))

        sorgu.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                katagory.clear()
                hashMap.clear()
                katagory.add(Katagory(50 , "#FFFFFF" , "NSP" , "" , false , sharedPreferencesManager.getBoolean("NSPExpend" , false)!! ))
                    for (i in snapshot.children) {
                            val value = i.getValue(Users::class.java)

                        if (value!!.project != null){
                            var hasmapKatagory  = mutableListOf<Katagory>()
                                for ((key, value) in value!!.project!!) {
                                    hasmapKatagory.add(Katagory(50 , value.colorCode , "NSP" , key , value.play , false))
                                }
                              hashMap.put("NSP" ,hasmapKatagory )
                            }
                    }
                setAdapterDefault()

            }
            override fun onCancelled(error: DatabaseError) {setAdapterDefault()
            }
        })
    }

    @SuppressLint("NewApi")
    fun firebaseUpdatePlay(katagory: Katagory){
        var project =  HashMap<String, Any>()
        project.put("play" , !katagory.run)
        project.put("colorCode" , katagory.color_code)
        project.put("projectName" , katagory.project_name)
        FirebaseDatabase.getInstance().getReference("users").child(sharedPreferencesManager.getString("phone" , "defaulUserPhone")!!).child("project").child(katagory.project_name)
            .setValue(project)

    }

    @SuppressLint("NewApi")
    fun updateFireBaseHistory(runHistory: RunHistory){
        var project =  HashMap<String, Any>()
        project.put("play" , runHistory.play)
        project.put("colorCode" , runHistory.color_code)
        project.put("projectName" , runHistory.project_name)
        project.put("total" , runHistory.daily_total)
        project.put("starDate" , runHistory.start_date)
        project.put("endDate" , runHistory.end_date)

        FirebaseDatabase.getInstance().getReference("users").child(sharedPreferencesManager.getString("phone" , "defaulUserPhone")!!).child("history")
            .child(time).child(runHistory.project_name)
            .setValue(project)
    }

    @SuppressLint("NewApi")
    fun checkIsOnline(size: Int){
        try{
            var online = false
            if (size != 0) {
                online = true
            }
            val updateValue = HashMap<String, Any>()
            updateValue["online"] = online
            firebase!!.child(
                sharedPreferencesManager.getString("phone", "userDefaultPhone").toString()
            ).updateChildren(updateValue)
        }catch (e:Exception){
            Toast.makeText(requireContext() , e.message , Toast.LENGTH_SHORT).show()
        }
    }

}
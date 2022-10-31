package com.example.m.ismayilov.timetracker
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.adapter.HistoryBaseAdapter
import com.example.m.ismayilov.timetracker.databinding.FragmentHistoryBinding
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.RunHistory
import com.example.m.ismayilov.timetracker.unkonown.Constant
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    lateinit var binding: FragmentHistoryBinding
    lateinit var view: FrameLayout
    lateinit var myRoomDatabase: MyRoomDatabase
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater ,container, false)
        view = binding.root
        fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")
        myRoomDatabase = MyRoomDatabase.getDatabase(requireContext())

        val args: HistoryFragmentArgs by navArgs()
        if (args.userHistory)
            getHistoryFirebase(args.phone)
        else
            getUniqTimeDatabase()

        return  view
    }

    fun  getHistoryFirebase(phone:String){
        var sorgu  = firebase!!.orderByChild("phone").equalTo(phone)

        sorgu.addValueEventListener(object: ValueEventListener {
            @SuppressLint("NewApi")
            override fun onDataChange(snapshot: DataSnapshot) {
                val historyBase  = mutableListOf<String>()
                var history = HashMap<String , MutableList<RunHistory>>()

                for (i in snapshot.children) {
                    val value = i.getValue(Users::class.java)

                    if (value!!.online != null){
                        for ((key, value) in value.history!!) {
                            historyBase.add(key)
                            var list = mutableListOf<RunHistory>()
                            for ((key, value) in value){
                                list.add(RunHistory(50 , 50 , value.colorCode , "NSP" , value.projectName
                                    ,value.starDate , value.endDate , value.total , "" , value.play))
                            }
                            history.put(key , list )
                        }
                        setAdapterListView(historyBase , history)

                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
    fun getUniqTimeDatabase(){
        lifecycleScope.launch {
           val historyBase = myRoomDatabase.runDao().readUniqTime()
            var history = HashMap<String , MutableList<RunHistory>>()
//            var time:MutableList<String>
            for (i in historyBase){
                history.put(i , myRoomDatabase.runDao().readTime(i))
            }
            if(historyBase.size !=0  ) binding.historyEmpty.isVisible =false
            setAdapterListView(historyBase , history)
        }
    }

    fun setAdapterListView(date:MutableList<String> , history:HashMap<String , MutableList<RunHistory>>){
        val baseAdapter = HistoryBaseAdapter(requireContext() ,date , history)
        binding.historyRecyclerview.setHasFixedSize(true)
        binding.historyRecyclerview.setLayoutManager(GridLayoutManager(activity, 1))
        binding.historyRecyclerview.adapter = baseAdapter
    }

}
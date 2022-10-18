package com.example.m.ismayilov.timetracker
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.adapter.HistoryBaseAdapter
import com.example.m.ismayilov.timetracker.databinding.FragmentHistoryBinding
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.RunHistory
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    lateinit var binding: FragmentHistoryBinding
    lateinit var view: FrameLayout
    lateinit var myRoomDatabase: MyRoomDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater ,container, false)
        view = binding.root

        myRoomDatabase = MyRoomDatabase.getDatabase(requireContext())

        getUniqTimeDatabase()

        return  view
    }

    fun getUniqTimeDatabase(){
        lifecycleScope.launch {
           val historyBase = myRoomDatabase.runDao().readUniqTime()
            var history = HashMap<String , MutableList<RunHistory>>()
//            var time:MutableList<String>
            for (i in historyBase){
                history.put(i , myRoomDatabase.runDao().readTime(i))
            }
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
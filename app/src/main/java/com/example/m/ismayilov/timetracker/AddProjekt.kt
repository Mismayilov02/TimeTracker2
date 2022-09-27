package com.example.m.ismayilov.timetracker

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.databinding.FragmentAddProekBinding
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.example.m.ismayilov.timetracker.room.ProjektHistory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddProek : Fragment() {

    lateinit var myRoomDatabase: MyRoomDatabase
    lateinit var binding: FragmentAddProekBinding
    var view: FrameLayout? = null
    var defaultColor = "#ED1515"
    lateinit var recycleAdapte: ColorRecycleAdapte
    var historyAll: MutableList<String>? =
        mutableListOf("#ED1515", "#D85723", "#E1AF00", "#4A9F00", "#0FDFCA")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProekBinding.inflate(inflater, container, false)
        view = binding.root
        myRoomDatabase = MyRoomDatabase.getDatabase(requireContext())


        val args: AddProekArgs by navArgs()
        if (args.katagoryOrProyek) {
            binding.addProektName.setHint("Proyek adi")
        }

        lifecycleScope.launch {
            if (myRoomDatabase.colorDao().read_all_color().size != 0) {
                historyAll?.addAll(myRoomDatabase.colorDao().read_all_color())
            }
            recycleAdapte = ColorRecycleAdapte(requireContext(), historyAll!!)
            binding.addRecyclerView.setHasFixedSize(true)
            binding.addRecyclerView.setLayoutManager(GridLayoutManager(activity, 4))
            binding.addRecyclerView.adapter = recycleAdapte

            recycleAdapte.onItemClick = {
                binding.addViewCardview.setCardBackgroundColor(Color.parseColor(it))
                defaultColor = it
            }
        }

        binding.addAddBtn.setOnClickListener {
           if (binding.addProektName.text.isEmpty()){
               setSnakebarMessage(args.katagoryOrProyek , it)
           }else{
               saveDatabase(args.katagoryOrProyek , args.katagoryName)
           }
        }


        var keyListener = View.OnKeyListener { p0, p1, p2 ->
            if (p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                hideSoftKeyBoard(requireContext(), p0)
                true
            } else {
                false
            }
        }
        binding.addProektName.setOnKeyListener(keyListener)

        return view
    }

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun saveDatabase(katagoryOrProyek: Boolean  , katagoryName :String) {
        var katagory:Katagory
        lifecycleScope.launch {
            if (!katagoryOrProyek) {
                 katagory = Katagory(0, defaultColor, binding.addProektName.text.toString() , "null" ,false )

            } else {
                 katagory = Katagory(0, defaultColor , katagoryName , binding.addProektName.text.toString(),false )
            }
            myRoomDatabase.katagoryDao().write_katagoryr(katagory)
            findNavController().navigate(R.id.navigation_run_backprojekt)
        }
    }

    fun setSnakebarMessage(katagoryOrProyek: Boolean , it:View  ){
        if (!katagoryOrProyek) {
            Snackbar.make(it, "lutfen bir katagory ismi giriniz", Snackbar.LENGTH_SHORT).show()
        } else {
                Snackbar.make(it, "lutfen bir proyekt ismi giriniz", Snackbar.LENGTH_SHORT).show()
            }

        }

}

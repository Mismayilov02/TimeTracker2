package com.example.m.ismayilov.timetracker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.m.ismayilov.timetracker.databinding.FragmentAddProekBinding
import com.example.m.ismayilov.timetracker.room.Katagory
import com.example.m.ismayilov.timetracker.room.MyRoomDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class AddProek : Fragment() {

    lateinit var myRoomDatabase: MyRoomDatabase
    lateinit var binding: FragmentAddProekBinding
    var view: FrameLayout? = null
    var defaultColor = "#ED1515"
    var fireBaseDatabase: FirebaseDatabase? = null
    var firebase: DatabaseReference? = null
    lateinit var recycleAdapte: ColorRecycleAdapte
    var historyAll: MutableList<String>? =
        mutableListOf("#ED1515", "#D85723", "#E1AF00", "#4A9F00", "#0FDFCA" , "#FF9900" , "#FFA5A5" , "#00FFF0" , "#8E15ED" , "#ED15B1" , "#00FF29")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProekBinding.inflate(inflater, container, false)
        view = binding.root
        myRoomDatabase = MyRoomDatabase.getDatabase(requireContext())
        fireBaseDatabase = FirebaseDatabase.getInstance()
        firebase = fireBaseDatabase!!.getReference("users/")
        val args: AddProekArgs by navArgs()
        if (args.type.equals("project") || args.type.equals("online") || !args.projectName.equals("nulll")) {
            binding.addProektName.setHint("Project Name")
        }

        if (args.type.equals("changeName")){
            binding.addAddBtn.text = "Change"
        }


        lifecycleScope.launch {
            if (myRoomDatabase.colorDao().readAllColor().size != 0) {
                historyAll?.addAll(myRoomDatabase.colorDao().readAllColor())
            }
            recycleAdapte = ColorRecycleAdapte(requireContext(), historyAll!!)
            binding.addRecyclerView.setHasFixedSize(true)
            binding.addRecyclerView.setLayoutManager(GridLayoutManager(activity, 4))
            binding.addRecyclerView.adapter = recycleAdapte

            recycleAdapte.onItemClick = {
                binding.addIewArdview.setColorFilter(Color.parseColor(it))
                defaultColor = it
            }
        }

        binding.addAddBtn.setOnClickListener {
           if (binding.addProektName.text.isEmpty()){
               Snackbar.make(it, "Name cannot be blank", Snackbar.LENGTH_SHORT).show()
           }else{
               saveDatabase(args.type , args.katagoryName , args.phone , args.projectName)
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

    fun saveDatabase(type:String, katagoryName :String , phone:String , projectName: String) {
        var katagory:Katagory
        lifecycleScope.launch {
            if (type.equals("project")) {
                katagory = Katagory(0, defaultColor , katagoryName , binding.addProektName.text.toString(),false  , false)
                myRoomDatabase.katagoryDao().writeKatagoryr(katagory)
                findNavController().navigate(R.id.action_addProek2_to_runScreen2)
            }else if (type.equals("online")){
                saveFirebaseUser(phone ,  binding.addProektName.text.toString() , defaultColor)
            }
            else if (type.equals("changeName")){
                changeNameDatabase(katagoryName , projectName)
            }
            else if (type.equals("onlineUpdateProject")){
                fireBaseDeleteValues(phone , projectName  )
                saveFirebaseUser(phone ,  binding.addProektName.text.toString() , defaultColor)
            }
            else {

                katagory = Katagory(0, defaultColor, binding.addProektName.text.toString() , "nulll" ,false ,false)
                myRoomDatabase.katagoryDao().writeKatagoryr(katagory)
                findNavController().navigate(R.id.action_addProek2_to_runScreen2)

            }

        }
    }

  fun changeNameDatabase(katagoryName: String , projectName: String){
      lifecycleScope.launch {
          if (projectName.equals("nulll")){
              myRoomDatabase.katagoryDao().updateKatagoryColor(katagoryName , defaultColor)
              myRoomDatabase.katagoryDao().updateKatagoryName(katagoryName , binding.addProektName.text.toString())
              myRoomDatabase.runDao().updateKatagoryColor(defaultColor , katagoryName )
              myRoomDatabase.runDao().updateKatagoryName( katagoryName , binding.addProektName.text.toString() )

          }else{
              myRoomDatabase.katagoryDao().updateProjectColor(defaultColor , projectName , katagoryName)
              myRoomDatabase.katagoryDao().updateProjectName(binding.addProektName.text.toString() , projectName ,katagoryName)
              myRoomDatabase.runDao().updateProjectName(katagoryName , projectName , binding.addProektName.text.toString() )
              myRoomDatabase.runDao().updateProjectColor(defaultColor ,katagoryName , projectName )
          }
          findNavController().navigate(R.id.action_addProek2_to_runScreen2)
      }

  }

    fun saveFirebaseUser(phone:String ,projectName: String , colorCode:String ){
        var project =  HashMap<String, String>()
        project.put("projectName" , projectName)
        project.put("colorCode" , colorCode)
        FirebaseDatabase.getInstance().getReference("users/").child(phone).child("project").child(projectName)
            .setValue(project)
        findNavController().navigate(R.id.action_addProek2_to_onlinenavhost)
    }

    fun fireBaseDeleteValues(phone: String , oldProjectName: String){
        firebase!!.child(phone).child("project").child(oldProjectName).removeValue()
    }

}

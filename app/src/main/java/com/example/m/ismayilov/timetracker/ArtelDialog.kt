package com.example.m.ismayilov.timetracker

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation

class ArtelDialog {

    fun getSuccesReportDialog(context: Context) {


    }

    fun getWifiDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.wifieror_dialoq)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.findViewById<ImageView>(R.id.wifierror_exit_btn).setOnClickListener {
            dialog.dismiss()
        }

    }

    fun getMaxDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.run_max_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.findViewById<ImageView>(R.id.run_max_btn).setOnClickListener {
            dialog.dismiss()
        }

    }

    fun getHaveDialog(context: Context  , v:View) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.have_user)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.findViewById<ImageView>(R.id.have_dismis_btn).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.have_login_btn).setOnClickListener {
            Navigation.findNavController(v).navigate(R.id.action_createFragment_to_login)
            dialog.dismiss()
        }

    }

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiinfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobildatainfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiinfo != null && wifiinfo.isConnected || mobildatainfo!!.isConnected && mobildatainfo != null
    }

}
package com.example.m.ismayilov.timetracker

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.api.Context
import org.json.JSONObject
import java.util.HashMap

class FCM {
    private val BASE_URL = "https://fcm.googleapis.com/fcm/send"
    private val SERVER_KEY =
        "key=AAAArpzl5JI:APA91bH_YatQFGB5KoTQ823yMKs9OBM3G4QghTmRTp9VwkqFrTu60c5_DyY2EgESGVGEg5GrmJDpKwV1JPMFtXqTmROq4_CfXPs8_J2CSUjNVzmfg6DjSWVumTCBhrwkBiExoWyJp-No"
    fun pushNofication(context: android.content.Context, title: String?, messasge: String?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val queue = Volley.newRequestQueue(context)
        try {
            val json = JSONObject()
            json.put("to", "/topics/all")
            val notification = JSONObject()
            notification.put("title", title)
            notification.put("body", messasge)
            json.put("notification", notification)
            val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, BASE_URL, json,
                Response.Listener { response -> println(response) },
                Response.ErrorListener { error -> println(error) }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Content-type"] = "application/json"
                    params["Authorization"] = SERVER_KEY
                    return params
                }
            }
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            println(e)
        }
    }
}
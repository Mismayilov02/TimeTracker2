package com.example.m.ismayilov.timetracker

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi

class Constant {
    @RequiresApi(Build.VERSION_CODES.N)
    val simpleToDay  = SimpleDateFormat("yyyy-MM-dd")

    companion object{
        const val SERVERY_KEY = "BDzjOJCrzrb1AlylmsS4p6HmZmA7EQbVOQFqbZSNvZp0UPafDGP8ya5nUBO29FMgvC8VzqE6VBy8wbnrGvO9z5M"
        const val BASE_URL = "hhtps://fcm.googleapis.com"
        const val  CONTENT_TYPE = "application/json"
    }

}
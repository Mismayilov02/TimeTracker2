package com.example.m.ismayilov.timetracker.room

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "projekt_history")
data  class ProjektHistory(@PrimaryKey(autoGenerate = true)
                           @ColumnInfo(name = "id") var id:Int,
                           @ColumnInfo(name = "color_code") var color_code:String,
                           @ColumnInfo(name = "katagory_name") var katagory_name:String,
                           @ColumnInfo(name = "project_name") var project_name:String,
                           @ColumnInfo(name = "start_date") var start_date:Long,
                           @ColumnInfo(name = "end_date") var end_date:Long,
                           @ColumnInfo(name = "daily_total") var daily_total:Long,
                           @ColumnInfo(name = "date") var date:String,
                           @ColumnInfo(name = "play") var play:Boolean) {
}
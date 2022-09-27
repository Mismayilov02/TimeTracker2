package com.example.m.ismayilov.timetracker.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_color")
data  class HistoryColor(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "id") var id:Int,
                         @ColumnInfo(name = "color_code") var color_code:String) {
}
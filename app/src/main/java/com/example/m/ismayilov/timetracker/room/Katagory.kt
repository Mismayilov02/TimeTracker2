package com.example.m.ismayilov.timetracker.room

import android.annotation.SuppressLint
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "katagory")
data class Katagory(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id") var id:Int,
                    @ColumnInfo(name = "color_code") var color_code:String,
                    @SuppressLint("KotlinNullnessAnnotation") @ColumnInfo(name = "katagory_name")
                    @Nullable
                    var katagory_name:String,
                    @ColumnInfo(name = "project_name") var project_name:String?,
                    @ColumnInfo(name = "run") var run:Boolean) {
}
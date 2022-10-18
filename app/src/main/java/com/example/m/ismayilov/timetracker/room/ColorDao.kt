package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ColorDao {
    @Query("select color_code from history_color")
    suspend fun readAllColor() : MutableList<String>

    @Insert
    suspend fun writeColor(historyColor: HistoryColor)
}
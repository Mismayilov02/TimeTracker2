package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.Nonnull
import org.jetbrains.annotations.NotNull

@Dao
interface ProjectDao {


    @Query("select * from projekt_history where play =:play ")
    suspend fun read_all_katagory(play :Boolean) : MutableList<ProjektHistory>

    @Query("select * from projekt_history where project_name = not null and play = :play ")
    suspend fun read_notnull_katagory(play :Boolean) : MutableList<ProjektHistory>

    @Query("select * from projekt_history where project_name = null and play = :play ")
    suspend fun read_null_katagory(play :Boolean) : MutableList<ProjektHistory>

//    @NotNull
//    @Query("SELECT DISTINCT project_name from projekt_history")
//     suspend  fun read_distinc_katagory() : MutableList<ProjektHistory>

    @Query("update projekt_history set play =:play where id= :id")
    suspend  fun update_history_play(id: Int, play: Boolean)

    @Insert
    suspend fun write_katagoryr(projektHistory: ProjektHistory)
}
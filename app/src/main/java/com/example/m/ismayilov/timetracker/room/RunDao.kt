package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RunDao {

    @Query("select * from run_history where play =:play ")
    suspend fun readAllKatagory(play :Boolean) : MutableList<RunHistory>

    @Query("select * from run_history")
    suspend fun readAll() : MutableList<RunHistory>

    @Query("select * from run_history where  play = :play ")
    suspend fun readNotnullKatagory(play :Boolean) : MutableList<RunHistory>

    @Query("select * from run_history where project_name = null and play = :play ")
    suspend fun readNullKatagory(play :Boolean) : MutableList<RunHistory>

    @Query("select * from run_history where katagory_name=:katagoryName and project_name=:projectName limit 1")
    suspend fun readDalyTrue(katagoryName:String , projectName:String) : RunHistory

    @Query("select * from run_history where date=:date ")
    suspend fun readTime(date:String) :MutableList<RunHistory>

    @Query("select * from run_history where play=:play and  katagory_name = 'NSP' ")
    suspend fun readNSPRun(play: Boolean) :MutableList<RunHistory>

    @Query("select DISTINCT date from run_history order by start_date desc  ")
    suspend fun readUniqTime() : MutableList<String>

    @Query("update run_history set play =:play where id= :id")
    suspend  fun updateHistoryPlay(id: Int, play: Boolean)

    @Query("update run_history set play =:total where id= :id")
    suspend  fun updateTotal(id: Int, total: Long)

    @Insert
    suspend fun inertRunHistory(runHistory: RunHistory)

    @Update
    suspend  fun updateRun(runHistory: RunHistory)
}
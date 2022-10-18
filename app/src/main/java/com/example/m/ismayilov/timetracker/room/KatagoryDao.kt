package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface KatagoryDao {

    @Query("select * from katagory")
    suspend fun readAllKatagory() : MutableList<Katagory>

    @Query("select * from katagory where project_name = :projectname")
    suspend fun readNullKatagory(projectname :String?) : MutableList<Katagory>

    @Query("select * from katagory where id = :id")
    suspend fun readId(id: Int ) : Katagory

    @Query("select * from katagory where katagory_name = :katagory_name and project_name != 'null'")
    suspend fun readKatagory(katagory_name :String ) : MutableList<Katagory>

    @Query("update katagory set run =:run where id= :id")
    suspend fun updateRun(id: Int, run: Boolean)

    @Query("update katagory set expend =:expend where id= :id")
    suspend fun updateExpendValues(id: Int, expend: Boolean)

    @Query("select * from katagory where  run =:run")
    suspend fun readNotNullKatagory(run:Boolean) : MutableList<Katagory>

    @Insert
    suspend fun writeKatagoryr(katagory: Katagory)

    @Update
    suspend  fun updateKatagory(katagory: Katagory)
}
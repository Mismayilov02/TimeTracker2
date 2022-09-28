package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KatagoryDao {

    @Query("select * from katagory")
    suspend fun read_all_katagory() : MutableList<Katagory>

    @Query("select * from katagory where project_name = :projectname")
    suspend fun read_null_katagory(projectname :String?) : MutableList<Katagory>

    @Query("select * from katagory where katagory_name = :katagory_name and project_name != 'null'")
    suspend fun read_katagory(katagory_name :String ) : MutableList<Katagory>

    @Query("select * from katagory where project_name = not null and run =:run")
    suspend fun read_notNull_katagory(run:Boolean) : MutableList<Katagory>

    @Insert
    suspend fun write_katagoryr(katagory: Katagory)
}
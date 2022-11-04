package com.example.m.ismayilov.timetracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface KatagoryDao {

    @Query("select * from katagory")
    suspend fun readAllKatagory() : MutableList<Katagory>

    @Query("select * from katagory where project_name=:project and katagory_name=:katagory")
    suspend fun readCheckKatagory(katagory:String , project:String) : Katagory

    @Query("select * from katagory where project_name = :projectname")
    suspend fun readNullKatagory(projectname :String?) : MutableList<Katagory>

    @Query("select * from katagory where id = :id")
    suspend fun readId(id: Int ) : Katagory

    @Query("select * from katagory where katagory_name = :katagory_name and project_name != 'nulll'")
    suspend fun readKatagory(katagory_name :String ) : MutableList<Katagory>

    @Query("update katagory set run =:run where id= :id")
    suspend fun updateRun(id: Int, run: Boolean)

    @Query("update katagory set project_name =:project where id= :id")
    suspend fun updateRunID(id: Int, project: String)

    @Query("update katagory set katagory_name =:setKatagoryName where katagory_name= :katagory")
    suspend fun updateKatagoryName(katagory: String , setKatagoryName:String)

    @Query("update katagory set color_code =:colorCode where katagory_name= :katagory and project_name = 'nulll'")
    suspend fun updateKatagoryColor(katagory: String , colorCode:String)

    @Query("update katagory set project_name =:setProjectName where katagory_name= :katagory and project_name=:project")
    suspend fun updateProjectName(setProjectName: String, project: String , katagory: String)

    @Query("update katagory set color_code =:setColorCode where katagory_name= :katagory and project_name=:project")
    suspend fun updateProjectColor(setColorCode: String, project: String , katagory: String)

    @Query("update katagory set expend =:expend where id= :id")
    suspend fun updateExpendValues(id: Int, expend: Boolean)

    @Query("select * from katagory where  run =:run")
    suspend fun readNotNullKatagory(run:Boolean) : MutableList<Katagory>

    @Insert
    suspend fun writeKatagoryr(katagory: Katagory)

    @Update
    suspend  fun updateKatagory(katagory: Katagory)


    @Query("delete from katagory where project_name=:project and katagory_name=:katagory ")
    suspend fun deleteProject(katagory:String , project:String)


    @Query("delete from katagory where katagory_name=:katagory")
    suspend fun deleteKatagory(katagory:String)

}
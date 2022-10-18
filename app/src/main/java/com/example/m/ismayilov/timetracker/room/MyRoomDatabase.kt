package com.example.m.ismayilov.timetracker.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [HistoryColor::class , Katagory::class , RunHistory::class ] , version = 1 , exportSchema = false)
abstract  class MyRoomDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
    abstract fun katagoryDao():KatagoryDao
    abstract fun runDao():RunDao


    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MyRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyRoomDatabase::class.java,
                "history"
            )
                .build()
        }
    }
}
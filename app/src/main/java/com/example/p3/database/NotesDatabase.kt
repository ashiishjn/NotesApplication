package com.example.p3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.p3.model.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun NotesDao(): NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context,
                            NotesDatabase::class.java,
                            "UserDB")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}
package com.example.p3.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.p3.model.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun addNotes(notes: Notes)

    @Query("SELECT * FROM Notes")
    fun getNotes() : LiveData<List<Notes>>

    @Query("SELECT * FROM Notes where title like :text or content like :text")
    fun getSpecificNotes(text : String) : LiveData<List<Notes>>

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Update
    suspend fun updateNotes(notes: Notes)
}
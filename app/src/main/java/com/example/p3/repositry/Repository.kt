package com.example.p3.repositry

import androidx.lifecycle.LiveData
import com.example.p3.database.NotesDatabase
import com.example.p3.model.Notes

class Repository(private val notesDatabase: NotesDatabase) {

    fun getNotes() : LiveData<List<Notes>> {
        return notesDatabase.NotesDao().getNotes()
    }

    suspend fun addNotes(notes: Notes) {
        notesDatabase.NotesDao().addNotes(notes)
    }

    suspend fun deleteNotes(notes: Notes) {
        notesDatabase.NotesDao().deleteNotes(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        notesDatabase.NotesDao().updateNotes(notes)
    }

    fun getSpecificNotes(text: String) : LiveData<List<Notes>> {
        return notesDatabase.NotesDao().getSpecificNotes(text)
    }

}
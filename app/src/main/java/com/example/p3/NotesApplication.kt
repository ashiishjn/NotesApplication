package com.example.p3

import android.app.Application
import com.example.p3.database.NotesDatabase
import com.example.p3.repositry.Repository

class NotesApplication : Application() {
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val database = NotesDatabase.getDatabase(applicationContext)
        repository = Repository(database)
    }
}
package com.example.p3.editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.p3.MainViewModel
import com.example.p3.NotesApplication
import com.example.p3.R
import com.example.p3.ViewModelFactory
import com.example.p3.databinding.ActivityNoteEditterBinding
import com.example.p3.model.Notes
import com.google.gson.Gson

class NoteEditterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditterBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var note : Notes
    private var flag : Boolean = true
    private var noteId : Int = 0

    override fun onBackPressed() {
        if(flag)
            updateNote()
        else
            addNote()

        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if(extras != null) {
            val nav = extras.getString("Navigation")
            if(nav == "Old Note"){
                flag = true
                val jsonObject = extras.getString("UserDetails")
                note = Gson().fromJson(jsonObject, Notes::class.java)
                binding.noteTitle.setText(note.title)
                binding.noteContent.setText(note.content)
                noteId = note.id
            }
            else if (nav == "New Note") {
                flag = false
            }
        }

        val repository = (application as NotesApplication).repository

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository, this)
        )[MainViewModel::class.java]

        binding.backArrow.setOnClickListener {
            if(flag)
                updateNote()
            else
                addNote()

            finish()
        }

        binding.menuIcon.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.apply {
                menuInflater.inflate(R.menu.extra_options, menu)
                setOnMenuItemClickListener {
                    when(it.itemId) {

                        R.id.delete -> deleteNote()
                    }
                    true
                }
            }
            popup.setForceShowIcon(true)
            popup.show()
        }

    }

    private fun addNote() {
        if(!(binding.noteTitle.text.toString().isEmpty() && binding.noteContent.text.toString().isEmpty())) {
            mainViewModel.addUserDetails(
                Notes(0, binding.noteTitle.text.toString(), binding.noteContent.text.toString())
            )
        }
    }

    private fun updateNote() {
        mainViewModel.updateUserDetails(
                Notes(noteId , binding.noteTitle.text.toString(), binding.noteContent.text.toString())
            )
    }

    private fun deleteNote() {
        if(flag)
            mainViewModel.deleteUserDetails(note)
        finish()
    }
}
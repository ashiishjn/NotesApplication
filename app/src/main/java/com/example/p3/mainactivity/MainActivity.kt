package com.example.p3.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.p3.MainViewModel
import com.example.p3.NotesApplication
import com.example.p3.ViewModelFactory
import com.example.p3.databinding.ActivityMainBinding
import com.example.p3.databinding.BottomSheetsBinding
import com.example.p3.editor.NoteEditterActivity
import com.example.p3.model.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as NotesApplication).repository

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository, this)
        )[MainViewModel::class.java]

        // *************************************************************************************

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = "%${s.toString()}%"
                mainViewModel.getSpecificNotes(text)

            }
        })

        mainViewModel.notesLiveData.observe(this) {
            notesAdapter.submitList(it)
        }

        notesAdapter = NotesAdapter(::onNoteClicked, this, ::onLongPress)

        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = notesAdapter

        binding.addFab.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteEditterActivity::class.java)
            intent.putExtra("Navigation", "New Note")
            startActivity(intent)
        }

        mainViewModel.getUserDetails().observe(this) {
            notesAdapter.submitList(it)
        }
    }

    private fun onNoteClicked(notes: Notes) {
        val intent = Intent(this@MainActivity, NoteEditterActivity::class.java)
        intent.putExtra("UserDetails", Gson().toJson(notes))
        intent.putExtra("Navigation", "Old Note")
        startActivity(intent)
    }

    private fun onLongPress(notes: Notes) {
        showOnLongPressDialog(notes)
    }

    private lateinit var sheetBinding: BottomSheetsBinding

    private fun showOnLongPressDialog(notes: Notes) {
        sheetBinding = BottomSheetsBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(sheetBinding.root)
        sheetBinding.delete.setOnClickListener {
            mainViewModel.deleteUserDetails(notes)
            dialog.dismiss()
        }
        sheetBinding.edit.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteEditterActivity::class.java)
            intent.putExtra("UserDetails", Gson().toJson(notes))
            intent.putExtra("Navigation", "Old Note")
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
}
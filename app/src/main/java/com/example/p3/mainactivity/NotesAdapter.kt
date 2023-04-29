package com.example.p3.mainactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.p3.databinding.CustomTextViewBinding
import com.example.p3.model.Notes

class NotesAdapter(
    private val onNoteClicked: (Notes) -> Unit,
    context: Context,
    private val onLongPress: (Notes) -> Unit ) :
    ListAdapter<Notes, NotesAdapter.NotesViewHolder>(ComparatorDiffUtil()) {

    private val context : Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = CustomTextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class NotesViewHolder(private val binding: CustomTextViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Notes) {
            if(note.title.isNotEmpty()) {
                binding.notesTitle.visibility = View.VISIBLE
                binding.notesTitle.text = note.title
            }
            if(note.content.isNotEmpty()) {
                binding.notesContent.visibility = View.VISIBLE
                binding.notesContent.text = note.content
            }
            binding.root.setOnLongClickListener {
                onLongPress(note)
                true
            }
            binding.root.setOnClickListener {
                onNoteClicked(note)
            }
//            binding.innerView.setOnClickListener {
//                onNoteClicked(details)
//            }
//
//            binding.deleteButton.setOnClickListener {
//                onDeleteButtonClicked(details)
//            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }
}
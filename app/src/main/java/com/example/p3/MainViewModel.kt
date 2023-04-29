package com.example.p3

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3.model.Notes
import com.example.p3.repositry.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, private val lifecycleOwner: LifecycleOwner) : ViewModel() {

    private val _notesLiveData = MutableLiveData<List<Notes>>()
    val notesLiveData get() = _notesLiveData

    fun getUserDetails() : LiveData<List<Notes>> {
        return repository.getNotes()
    }

    fun getSpecificNotes(text : String) {
        repository.getSpecificNotes(text).observe(lifecycleOwner) {
            notesLiveData.postValue(it)
        }
    }

    fun addUserDetails(notes: Notes) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.addNotes(notes)
        }
    }

    fun updateUserDetails(notes: Notes) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }

    fun deleteUserDetails(notes: Notes) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }
}
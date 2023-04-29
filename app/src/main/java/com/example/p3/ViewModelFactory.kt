package com.example.p3

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.p3.repositry.Repository

class ViewModelFactory(private val repository: Repository, private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, lifecycleOwner) as T
    }
}
package com.example.challenge3binar.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge3binar.viewModel.fragmentHome.ViewModelFragmentHome

class ViewModelFactory(private val menuRepository: RepositoryMenu) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelFragmentHome::class.java)) {
            return ViewModelFragmentHome(menuRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
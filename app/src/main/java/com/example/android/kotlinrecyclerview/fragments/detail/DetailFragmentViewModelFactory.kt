package com.example.android.kotlinrecyclerview.fragments.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.kotlinrecyclerview.entity.ViaplaySection

class DetailFragmentViewModelFactory(var section: ViaplaySection, val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java)) {
            return DetailFragmentViewModel(section,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
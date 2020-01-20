package com.example.timmytruong.wordsearch_19.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timmytruong.wordsearch_19.provider.GridProvider
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import javax.inject.Inject

class GridViewModelFactory @Inject constructor(private val gridProvider: GridProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return GridViewModel(gridProvider) as T
    }

}
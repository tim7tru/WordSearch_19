package com.example.timmytruong.wordsearch_19.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timmytruong.wordsearch_19.provider.InformationBarProvider
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel
import javax.inject.Inject

class InformationBarViewModelFactory @Inject constructor(private val informationBarProvider: InformationBarProvider): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return InformationBarViewModel(informationBarProvider = informationBarProvider) as T
    }

}
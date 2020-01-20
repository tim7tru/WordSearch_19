package com.example.timmytruong.wordsearch_19.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timmytruong.wordsearch_19.provider.SettingsProvider
import com.example.timmytruong.wordsearch_19.viewmodel.SettingsViewModel
import javax.inject.Inject

class SettingsViewModelFactory @Inject constructor(private val settingsProvider: SettingsProvider): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return SettingsViewModel(settingsProvider) as T
    }
}
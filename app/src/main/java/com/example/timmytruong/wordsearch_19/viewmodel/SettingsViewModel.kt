package com.example.timmytruong.wordsearch_19.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timmytruong.wordsearch_19.model.Word
import com.example.timmytruong.wordsearch_19.provider.SettingsProvider
import javax.inject.Inject

class SettingsViewModel(private val settingsProvider: SettingsProvider): ViewModel()
{
    fun setSavedWords(savedWords: ArrayList<Word>)
    {
        settingsProvider.setSavedWords(savedWords = savedWords)
    }

    fun getSavedWords(): ArrayList<Word>
    {
        return settingsProvider.getSavedWords()
    }

    fun clearSavedWords()
    {
        settingsProvider.clearSavedWords()
    }

    fun addSavedWord(word: Word)
    {
        settingsProvider.addSavedWord(word = word)
    }
}

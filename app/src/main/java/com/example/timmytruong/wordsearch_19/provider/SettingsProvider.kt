package com.example.timmytruong.wordsearch_19.provider

import com.example.timmytruong.wordsearch_19.model.Word
import javax.inject.Inject

class SettingsProvider @Inject constructor()
{
    private var savedWords: ArrayList<Word> = arrayListOf()

    fun setSavedWords(savedWords: ArrayList<Word>)
    {
        this.savedWords = savedWords
    }

    fun getSavedWords(): ArrayList<Word>
    {
        return savedWords
    }

    fun clearSavedWords()
    {
        savedWords.clear()
    }

    fun addSavedWord(word: Word)
    {
        savedWords.add(word)
    }
}
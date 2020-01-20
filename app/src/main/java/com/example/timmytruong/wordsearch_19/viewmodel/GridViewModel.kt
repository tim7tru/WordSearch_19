package com.example.timmytruong.wordsearch_19.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timmytruong.wordsearch_19.model.Letter
import com.example.timmytruong.wordsearch_19.model.Word
import com.example.timmytruong.wordsearch_19.provider.GridProvider
import javax.inject.Inject

class GridViewModel(private val gridProvider: GridProvider): ViewModel()
{
    private fun randomizeLetters(): ArrayList<Letter>
    {
        return gridProvider.randomizeLetters()
    }

    fun setWords(words: ArrayList<Word>)
    {
        gridProvider.setWords(wordsArray = words)
    }

    fun getWords(): ArrayList<Word>
    {
        return gridProvider.getWords()
    }

    fun setLetters(letterIndex: Int? = null, letter: Char? = null)
    {
        if (letter == null && letterIndex == null)
        {
            gridProvider.setLetters(lettersArray = randomizeLetters())
        }
        else if (letter != null && letterIndex != null)
        {
            gridProvider.setLetter(letterIndex = letterIndex, letter = letter)
        }
    }

    fun setChangeLetterState(letterIndex: Int? = null)
    {
        gridProvider.setLetterState(letterIndex = letterIndex)
    }

    fun getLetters(): ArrayList<Letter>
    {
        return gridProvider.getLetters()
    }

    fun getWordPositionByWord(word: String): Int
    {
        return gridProvider.getWordPostiionByWord(wordToBeFound = word)
    }

    fun containsWord(word: String): Boolean
    {
        return gridProvider.containsWord(word)
    }
}
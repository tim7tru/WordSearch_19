package com.example.timmytruong.wordsearch_19.viewmodel

import android.view.View
import com.example.timmytruong.wordsearch_19.models.GridModel
import com.example.timmytruong.wordsearch_19.utils.AppConstants

class GridViewModel
{
    private var gridModel: GridModel = GridModel()
    private var wordsHashMap: LinkedHashMap<String, Boolean>
    private var changedLetterStateHashMap: LinkedHashMap<Int, Boolean>
    private var lettersHashMap: LinkedHashMap<Int, Char>

    init
    {
        this.wordsHashMap = setDefaultWordsHashMap()
        this.changedLetterStateHashMap = defaultStates()
        this.lettersHashMap = randomizeLetters()
    }

    fun setDefaultWordsHashMap(): LinkedHashMap<String, Boolean>
    {
        val returnWords: LinkedHashMap<String, Boolean> = linkedMapOf()

        for (word in AppConstants.DEFAULT_WORDS)
        {
            returnWords.put(word, false)
        }

        gridModel.setWordsHashMap(returnWords)

        return returnWords
    }

    fun randomizeLetters(): LinkedHashMap<Int, Char>
    {
        val randomLetters: LinkedHashMap<Int, Char> = linkedMapOf()

        for (i in 0 until 100)
        {
            val randomElement: Int = (Math.random() * 26).toInt()
            randomLetters.put(i, AppConstants.UPPERCASE_LETTERS[randomElement])
        }

        gridModel.setLettersHashMap(randomLetters)

        return randomLetters
    }

    fun defaultStates(): LinkedHashMap<Int, Boolean>
    {
        val defaultStates: LinkedHashMap<Int, Boolean> = linkedMapOf()

        for (i in 0 until 100)
        {
            defaultStates.put(i, false)
        }

        gridModel.setStatesHashMap(defaultStates)

        return defaultStates
    }

    private fun saveData()
    {
        gridModel.setLettersHashMap(lettersHashMap)
        gridModel.setStatesHashMap(changedLetterStateHashMap)
        gridModel.setWordsHashMap(wordsHashMap)
    }

    fun setWordsHashMap(wordsHM: LinkedHashMap<String, Boolean>?)
    {
        if (wordsHM != null)
        {
            wordsHashMap = wordsHM
        }
        else
        {
            wordsHashMap = setDefaultWordsHashMap()
        }

        saveData()
    }

    fun getWordsHashMap(): LinkedHashMap<String, Boolean>
    {
        return gridModel.getWordsHashMap()
    }

    fun setLetters()
    {
        setLetters(null, null)
    }

    fun setLetters(letterIndex: Int?, letter: Char?)
    {
        if (letter == null && letterIndex == null)
        {
            lettersHashMap = randomizeLetters()
        }
        else if (letter != null && letterIndex != null)
        {
            lettersHashMap.put(letterIndex, letter)
        }

        saveData()
    }

    fun setChangeLetterState(letterIndex: Int)
    {
        changedLetterStateHashMap.put(letterIndex, true)
        saveData()
    }

    fun getChangeLetterState(): LinkedHashMap<Int, Boolean>
    {
        return gridModel.getLetterStateHashMap()
    }

    fun getLettersHashMap(): LinkedHashMap<Int, Char>
    {
        return gridModel.getLettersHashMap()
    }
}
package com.example.timmytruong.wordsearch_19.viewmodel

import com.example.timmytruong.wordsearch_19.models.GridModel
import com.example.timmytruong.wordsearch_19.utils.AppConstants
import kotlin.properties.Delegates

class GridViewModel
{
    private var gridModel: GridModel by Delegates.notNull()

    init
    {
        val wordsHashMap: LinkedHashMap<String, Boolean> = linkedMapOf()
        val changedLetterStateHashMap: LinkedHashMap<Int, Boolean> = linkedMapOf()
        val lettersHashMap: LinkedHashMap<Int, Char> = linkedMapOf()
        gridModel = GridModel(wordsHashMap, lettersHashMap, changedLetterStateHashMap)
    }

    fun setWordsHashMap(wordsArrayList: ArrayList<String>?)
    {
        if (wordsArrayList != null)
        {
            for (word in wordsArrayList)
            {
                gridModel.put(word, false)

            }
        }
        else
        {
            for (word in AppConstants.DEFAULT_WORDS)
            {
                wordsHashMap.put(word, false)
            }
        }
    }

    fun getWordsHashMap(): LinkedHashMap<String, Boolean>
    {
        return wordsHashMap;
    }

    fun setLetters()
    {
        setLetters(null, null)
    }

    fun setLetters(letterIndex: Int?, letter: Char?)
    {
        if (letter == null && letterIndex == null)
        {
            for (i in 0 until 100)
            {
                val randomElement: Int = (Math.random() * 26).toInt()
                lettersHashMap.put(i, AppConstants.UPPERCASE_LETTERS[randomElement])
                changedLetterStateHashMap.put(i, false)
            }
        }
        else if (letter != null && letterIndex != null)
        {
            lettersHashMap.put(letterIndex, letter)
        }
    }

    fun setChangeLetterState(letterIndex: Int)
    {
        changedLetterStateHashMap.put(letterIndex, true)
    }

    fun getChangeLetterState(): LinkedHashMap<Int, Boolean>
    {
        return changedLetterStateHashMap;
    }

    fun getLettersHashMap(): LinkedHashMap<Int, Char>
    {
        return lettersHashMap
    }
}
package com.example.timmytruong.wordsearch_19.provider

import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants

class GridProvider
{

    private var wordsHashMap: LinkedHashMap<String, Boolean> = linkedMapOf()
    private var changedLetterStateHashMap: LinkedHashMap<Int, Boolean> = linkedMapOf()
    private var lettersHashMap: LinkedHashMap<Int, Char> = linkedMapOf()


    fun setWordsHashMap(wordsArrayList: ArrayList<String>?)
    {
        if (wordsArrayList != null)
        {
            for (word in wordsArrayList)
            {
                wordsHashMap.put(word, false)
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
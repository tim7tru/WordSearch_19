package com.example.timmytruong.wordsearch_19.models

class GridModel
{
    private var wordsHashMap: HashMap<String, Boolean> = linkedMapOf()

    private var lettersHashMap: LinkedHashMap<Int, Char> = linkedMapOf()

    private var letterStateHashMap: LinkedHashMap<Int, Boolean> = linkedMapOf()

    fun setWordsHashMap(wordsHashMap: HashMap<String, Boolean>)
    {
        this.wordsHashMap = wordsHashMap
    }

    fun setLettersHashMap(lettersHashMap: LinkedHashMap<Int, Char>)
    {
        this.lettersHashMap = lettersHashMap
    }

    fun setStatesHashMap(statesHashMap: LinkedHashMap<Int, Boolean>)
    {
        this.letterStateHashMap = statesHashMap
    }

    fun getWordsHashMap(): HashMap<String, Boolean>
    {
        return wordsHashMap
    }

    fun getLettersHashMap(): LinkedHashMap<Int, Char>
    {
        return lettersHashMap
    }

    fun getLetterStateHashMap(): LinkedHashMap<Int, Boolean>
    {
        return letterStateHashMap
    }

}
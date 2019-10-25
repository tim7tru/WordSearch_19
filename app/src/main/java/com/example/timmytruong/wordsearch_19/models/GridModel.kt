package com.example.timmytruong.wordsearch_19.models

class GridModel(words: LinkedHashMap<String, Boolean>, letters: LinkedHashMap<Int, Char>, states: LinkedHashMap<Int, Boolean>)
{
    private var wordsHashMap: LinkedHashMap<String, Boolean> = linkedMapOf()

    private var lettersHashMap: LinkedHashMap<Int, Char> = linkedMapOf()

    private var letterStateHashMap: LinkedHashMap<Int, Boolean> = linkedMapOf()

    init
    {
        this.wordsHashMap = words
        this.lettersHashMap = letters
        this.letterStateHashMap = states
    }

    fun setWordsHashMap(wordsHashMap: LinkedHashMap<String, Boolean>)
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

    fun getWordsHashMap(): LinkedHashMap<String, Boolean>
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
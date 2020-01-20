package com.example.timmytruong.wordsearch_19.provider

import com.example.timmytruong.wordsearch_19.model.Letter
import com.example.timmytruong.wordsearch_19.model.Word
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants
import javax.inject.Inject

class GridProvider @Inject constructor()
{
    private var wordsArray: ArrayList<Word> = arrayListOf()

    private var lettersArray: ArrayList<Letter> = arrayListOf()

    init
    {
        this.wordsArray = setDefaultWords()
        this.lettersArray = randomizeLetters()
    }

    fun setWords(wordsArray: ArrayList<Word>)
    {
        this.wordsArray = wordsArray
    }

    fun setLetters(lettersArray: ArrayList<Letter>)
    {
        this.lettersArray = lettersArray
    }

    fun getWords(): ArrayList<Word>
    {
        return wordsArray
    }

    fun getLetters(): ArrayList<Letter>
    {
        return lettersArray
    }


    fun setDefaultWords(): ArrayList<Word>
    {
        val returnWords: ArrayList<Word> = arrayListOf()

        for (index in AppConstants.DEFAULT_WORDS.indices)
        {
            returnWords.add(Word(index = index, word = AppConstants.DEFAULT_WORDS[index], beenFound = false))
        }

        setWords(returnWords)

        return returnWords
    }

    fun setLetter(letterIndex: Int, letter: Char)
    {
        lettersArray[letterIndex].letter = letter
    }

    fun randomizeLetters(): ArrayList<Letter>
    {
        val randomLetters: ArrayList<Letter> = arrayListOf()

        for (i in 0 until 100)
        {
            val randomElement: Int = (Math.random() * 26).toInt()
            randomLetters.add(Letter(position = i, letter = AppConstants.UPPERCASE_LETTERS[randomElement], hasChanged = false))
        }

        setLetters(randomLetters)

        return randomLetters
    }

    fun setLetterState(letterIndex: Int? = null)
    {
        if (letterIndex != null)
        {
            lettersArray[letterIndex].hasChanged = true
        }
        else
        {
            for (i in 0 until 100)
            {
                lettersArray[i].hasChanged = false
            }
        }
    }

    fun getWordPostiionByWord(wordToBeFound: String): Int
    {
        for (word in wordsArray)
        {
            if (word.word == wordToBeFound)
            {
                return word.index
            }
        }
        return -1
    }

    fun containsWord(wordToCheck: String): Boolean
    {
        for (word in wordsArray)
        {
            if (word.word == wordToCheck)
            {
                return true
            }
        }
        return false
    }
}

package com.example.timmytruong.wordsearch_19.adapters

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TableLayout
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.utils.AppConstants
import com.example.timmytruong.wordsearch_19.utils.DrawUtils
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import java.util.ArrayList
import kotlin.properties.Delegates
import kotlin.random.Random

class GridAdapter(context: Context,
                  gridHandler: GridHandler,
                  gridView: GridView,
                  gridFrameLayout: FrameLayout,
                  wordTableLayout: TableLayout,
                  gridViewModel: GridViewModel)
{
    var context: Context by Delegates.notNull()

    var gridHandler: GridHandler by Delegates.notNull()

    var gridView: GridView by Delegates.notNull()

    var gridFrameLayout: FrameLayout by Delegates.notNull()

    var wordTableLayout: TableLayout by Delegates.notNull()

    var gridViewModel: GridViewModel by Delegates.notNull()

    init
    {
        this.context = context
        this.gridHandler = gridHandler
        this.gridView = gridView
        this.gridFrameLayout = gridFrameLayout
        this.wordTableLayout = wordTableLayout
        this.gridViewModel = gridViewModel
    }

    fun setupGrid()
    {
        gridViewModel.setLetters()
    }

    fun setupWords()
    {
        setupWords(null)
    }

    fun setupWords(newWords: ArrayList<String>?)
    {
        if (newWords == null)
        {
            gridViewModel.setWordsHashMap()
        }
        else
        {
            gridViewModel.setWordsHashMap(newWords)
        }
    }

    private fun checkIsSizedCorrectly(startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int): Boolean
    {
        if (startIndexOfWord >= 0 && directionOfWord <= 7)
        {
            when (directionOfWord)
            {
                AppConstants.DIRECTION_STRAIGHT_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 9) - startIndexOfWord) >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_DOWN_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 9) - startIndexOfWord) >= lengthOfWord && 9 - (startIndexOfWord / 10) >= lengthOfWord)
                AppConstants.DIRECTION_STRAIGHT_DOWN -> return (9 - (startIndexOfWord / 10) >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_DOWN_LEFT -> return ((startIndexOfWord - ((startIndexOfWord / 10) * 10) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord))
                AppConstants.DIRECTION_STRAIGHT_LEFT -> return ((startIndexOfWord - ((startIndexOfWord / 10) * 10) >= lengthOfWord))
                AppConstants.DIRECTION_DIAGONAL_UP_LEFT -> return ((startIndexOfWord - ((startIndexOfWord / 10) * 10) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord))
                AppConstants.DIRECTION_STRAIGHT_UP -> return ((startIndexOfWord / 10) + 1 >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_UP_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 9) - startIndexOfWord) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord)
            }
        }
        return false
    }

    private fun checkIsPositionedCorrectly(word: String, startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int): Boolean
    {
        var canBePositioned = false

        var letterIndex: Int = startIndexOfWord

        for (letterPositionInWord in 0 until lengthOfWord)
        {
            canBePositioned = letterConflictChecker(word, letterIndex, letterPositionInWord)

            if (!canBePositioned)
            {
                return canBePositioned
            }

            when (directionOfWord)
            {
                AppConstants.DIRECTION_STRAIGHT_RIGHT -> letterIndex++
                AppConstants.DIRECTION_DIAGONAL_DOWN_RIGHT -> letterIndex += 11
                AppConstants.DIRECTION_STRAIGHT_DOWN -> letterIndex += 10
                AppConstants.DIRECTION_DIAGONAL_DOWN_LEFT -> letterIndex += 9
                AppConstants.DIRECTION_STRAIGHT_LEFT -> letterIndex--
                AppConstants.DIRECTION_DIAGONAL_UP_LEFT -> letterIndex -= 11
                AppConstants.DIRECTION_STRAIGHT_UP -> letterIndex -= 10
                AppConstants.DIRECTION_DIAGONAL_UP_RIGHT -> letterIndex -= 9
            }
        }
        return canBePositioned
    }

    private fun letterConflictChecker(word: String, letterIndex: Int, letterPositionInWord: Int): Boolean
    {
        if (letterIndex <= AppConstants.NUMBER_OF_CELLS)
        {
            when (gridViewModel.getChangeLetterState().values.elementAt(letterIndex))
            {
                true -> return (gridViewModel.getLettersHashMap().values.elementAt(letterIndex) == word.toCharArray()[letterPositionInWord])
                false -> return true
            }
        }
        return false
    }

    private fun changeLetterArray(startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int, word: String)
    {
        var letterIndex: Int = startIndexOfWord

        for (letter in 0 until lengthOfWord)
        {
            gridViewModel.setChangeLetterState(letterIndex)
            gridViewModel.setLetters(letterIndex, word[letter])

            when (directionOfWord)
            {
                AppConstants.DIRECTION_STRAIGHT_RIGHT -> letterIndex++
                AppConstants.DIRECTION_DIAGONAL_DOWN_RIGHT -> letterIndex += 11
                AppConstants.DIRECTION_STRAIGHT_DOWN -> letterIndex += 10
                AppConstants.DIRECTION_DIAGONAL_DOWN_LEFT -> letterIndex += 9
                AppConstants.DIRECTION_STRAIGHT_LEFT -> letterIndex--
                AppConstants.DIRECTION_DIAGONAL_UP_LEFT -> letterIndex -= 11
                AppConstants.DIRECTION_STRAIGHT_UP -> letterIndex -= 10
                AppConstants.DIRECTION_DIAGONAL_UP_RIGHT -> letterIndex -= 9
            }
        }
    }

    fun setupWordGrid()
    {
        for (word in gridViewModel.getWordsHashMap().keys)
        {
            val lengthOfWord: Int = word.length

            var directionOfWord: Int = Random.nextInt(AppConstants.NUMBER_OF_DIRECTIONS)

            var startIndexOfWord: Int = Random.nextInt(AppConstants.NUMBER_OF_CELLS)

            var isPositionedCorrectly = false

            var isSizedCorrectly = false

            var count = 0

            while (!isSizedCorrectly)
            {
                isSizedCorrectly = checkIsSizedCorrectly(startIndexOfWord, lengthOfWord, directionOfWord)

                if (isSizedCorrectly)
                {

                    isPositionedCorrectly = checkIsPositionedCorrectly(word, startIndexOfWord, lengthOfWord, directionOfWord)

                    if (isPositionedCorrectly)
                    {
                        // Do Nothing
                        break
                    }
                    else
                    {
                        isSizedCorrectly = false
                    }
                }

                if (count != 8 && directionOfWord == 7)
                {
                    directionOfWord = 0
                    count++
                }
                else if (count != 8)
                {
                    directionOfWord++
                    count++
                }
                else if (count == 8)
                {
                    startIndexOfWord = Random.nextInt(AppConstants.NUMBER_OF_CELLS)
                    directionOfWord = Random.nextInt(AppConstants.NUMBER_OF_DIRECTIONS)
                    count = 0
                }
            }

            changeLetterArray(startIndexOfWord, lengthOfWord, directionOfWord, word)
        }
    }

    fun setupUI()
    {
        val letterAdapter = LetterAdapter(context, gridViewModel.getLettersHashMap().values)
        gridHandler.setLetters(context, letterAdapter, gridView)
        gridHandler.setTableLayout(context, gridViewModel.getWordsHashMap().keys, wordTableLayout)
    }
}
package com.example.timmytruong.wordsearch_19.adapters

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TableLayout
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel
import kotlin.random.Random

class GridAdapter(private val context: Context,
                  private val gridHandler: GridHandler,
                  private val gridView: GridView,
                  gridFrameLayout: FrameLayout,
                  private val wordTableLayout: TableLayout,
                  scoreTextView: TextView,
                  private val gridViewModel: GridViewModel,
                  informationBarHandler: InformationBarHandler,
                  private val informationBarViewModel: InformationBarViewModel)
{
    private val drawAdapter: DrawAdapter = DrawAdapter(context, gridFrameLayout,
            wordTableLayout, scoreTextView, gridHandler, gridViewModel, informationBarViewModel,
            informationBarHandler)

    private fun setupGrid()
    {
        gridViewModel.setLetters()
    }

    fun reset(newWords: HashMap<String, Boolean>)
    {
        setupGrid()
        setupWords(newWords)
        setupWordGrid()
        setupUI()
    }

    fun reset()
    {
        val resetHashMap: HashMap<String, Boolean> = gridViewModel.getWordsHashMap()

        for (word in 0 until resetHashMap.size)
        {
            resetHashMap[gridViewModel.getWordsHashMap().keys.elementAt(word)] = false
        }
        reset(resetHashMap)
    }

    private fun setupWords(newWords: HashMap<String, Boolean>)
    {
        gridViewModel.setWordsHashMap(newWords)
    }

    private fun checkIsSizedCorrectly(startIndexOfWord: Int, lengthOfWord: Int,
                                      directionOfWord: Int): Boolean
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

    private fun checkIsPositionedCorrectly(word: String, startIndexOfWord: Int, lengthOfWord: Int,
                                           directionOfWord: Int): Boolean
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

    private fun letterConflictChecker(word: String, letterIndex: Int,
                                      letterPositionInWord: Int): Boolean
    {
        if (letterIndex <= AppConstants.NUMBER_OF_CELLS)
        {
            return when (gridViewModel.getChangeLetterState().values.elementAt(letterIndex))
            {
                true -> (gridViewModel.getLettersHashMap().values.elementAt(
                        letterIndex) == word.toCharArray()[letterPositionInWord])
                false -> true
            }
        }
        return false
    }

    private fun changeLetterArray(startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int,
                                  word: String)
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

            var directionOfWord: Int = Random.nextInt(
                    AppConstants.NUMBER_OF_DIRECTIONS)

            var startIndexOfWord: Int = Random.nextInt(
                    AppConstants.NUMBER_OF_CELLS)

            var isPositionedCorrectly: Boolean

            var isSizedCorrectly = false

            var count = 0

            while (!isSizedCorrectly)
            {
                isSizedCorrectly = checkIsSizedCorrectly(startIndexOfWord, lengthOfWord,
                        directionOfWord)

                if (isSizedCorrectly)
                {

                    isPositionedCorrectly = checkIsPositionedCorrectly(word, startIndexOfWord,
                            lengthOfWord, directionOfWord)

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
                    startIndexOfWord = Random.nextInt(
                            AppConstants.NUMBER_OF_CELLS)
                    directionOfWord = Random.nextInt(
                            AppConstants.NUMBER_OF_DIRECTIONS)
                    count = 0
                }
            }

            changeLetterArray(startIndexOfWord, lengthOfWord, directionOfWord, word)
        }
    }

    fun setupUI()
    {
        val letterAdapter = LetterAdapter(context, gridViewModel.getLettersHashMap())
        gridHandler.setLetters(context, letterAdapter, gridView)
        gridHandler.setTableLayout(context, gridViewModel.getWordsHashMap().keys, wordTableLayout)
        gridHandler.setOnTouchListener(context, drawAdapter.getOnTouchListener(), gridView)
        informationBarViewModel.setTotalWords(gridViewModel.getWordsHashMap().size)
    }
}
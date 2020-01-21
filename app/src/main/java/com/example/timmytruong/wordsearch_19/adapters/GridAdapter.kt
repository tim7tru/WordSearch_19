package com.example.timmytruong.wordsearch_19.adapters

import android.content.Context
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TableLayout
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.model.Word
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
                  private var gridViewModel: GridViewModel,
                  informationBarHandler: InformationBarHandler,
                  private val informationBarViewModel: InformationBarViewModel)
{
    private val drawAdapter: DrawAdapter = DrawAdapter(
            context = context,
            gridFrame = gridFrameLayout,
            wordsTableLayout = wordTableLayout,
            scoreTextView = scoreTextView,
            gridHandler = gridHandler,
            gridViewModel = gridViewModel,
            informationBarViewModel = informationBarViewModel,
            informationBarHandler = informationBarHandler)

    private fun setupGrid()
    {
        gridViewModel.setLetters()
    }

    fun reset(newWords: ArrayList<Word>)
    {
        setupGrid()
        setupWords(newWords = newWords)
        setupWordGrid()
        setupUI()
    }

    fun reset()
    {
        val resetWords: ArrayList<Word> = gridViewModel.getWords()

        for (word in 0 until resetWords.size)
        {
            resetWords[word].beenFound = false
        }
        reset(newWords = resetWords)
    }

    private fun setupWords(newWords: ArrayList<Word>)
    {
        gridViewModel.setWords(words = newWords)
    }

    private fun checkIsSizedCorrectly(startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int): Boolean
    {
        if (startIndexOfWord >= 0 && directionOfWord <= 7)
        {
            when (directionOfWord)
            {
                AppConstants.DIRECTION_STRAIGHT_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 10) - startIndexOfWord) >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_DOWN_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 10) - startIndexOfWord) >= lengthOfWord && 10 - (startIndexOfWord / 10) >= lengthOfWord)
                AppConstants.DIRECTION_STRAIGHT_DOWN -> return (10 - (startIndexOfWord / 10) >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_DOWN_LEFT -> return ((startIndexOfWord - (((startIndexOfWord / 10) * 10) - 1) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord))
                AppConstants.DIRECTION_STRAIGHT_LEFT -> return ((startIndexOfWord - (((startIndexOfWord / 10) * 10) - 1) >= lengthOfWord))
                AppConstants.DIRECTION_DIAGONAL_UP_LEFT -> return ((startIndexOfWord - (((startIndexOfWord / 10) * 10) - 1) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord))
                AppConstants.DIRECTION_STRAIGHT_UP -> return ((startIndexOfWord / 10) + 1 >= lengthOfWord)
                AppConstants.DIRECTION_DIAGONAL_UP_RIGHT -> return (((((startIndexOfWord / 10) * 10) + 10) - startIndexOfWord) >= lengthOfWord && (startIndexOfWord / 10) + 1 >= lengthOfWord)
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
            canBePositioned = letterConflictChecker(word = word,
                    letterIndex = letterIndex,
                    letterPositionInWord = letterPositionInWord)

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
        if (letterIndex < AppConstants.NUMBER_OF_CELLS)
        {
            return when (gridViewModel.getLetters()[letterIndex].hasChanged)
            {
                true -> (gridViewModel.getLetters()[letterIndex].letter == word.toCharArray()[letterPositionInWord])
                false -> true
            }
        }
        return false
    }

    private fun changeLetterArray(startIndexOfWord: Int, lengthOfWord: Int, directionOfWord: Int, word: String)
    {
        var letterIndex: Int = startIndexOfWord

        for (letter in 0 until lengthOfWord)
        {
            gridViewModel.setChangeLetterState(letterIndex = letterIndex)
            gridViewModel.setLetters(letterIndex =letterIndex, letter = word[letter])

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

    private fun determineStartIndex(lengthOfWord: Int): Int
    {
        val randomElement = Random.nextInt(AppConstants.EDGE_CELLS.size)

        return if (lengthOfWord == 10)
        {
            AppConstants.EDGE_CELLS[randomElement]
        }
        else
        {
            Random.nextInt(AppConstants.NUMBER_OF_CELLS)
        }
    }


    fun setupWordGrid()
    {
        for (word in gridViewModel.getWords())
        {
            val lengthOfWord: Int = word.word.length

            var directionOfWord: Int = Random.nextInt(AppConstants.NUMBER_OF_DIRECTIONS)

            var startIndexOfWord: Int = determineStartIndex(lengthOfWord = lengthOfWord)

            var isPositionedCorrectly: Boolean

            var isSizedCorrectly = false

            var count = 0

            while (!isSizedCorrectly)
            {
                isSizedCorrectly = checkIsSizedCorrectly(startIndexOfWord = startIndexOfWord,
                        lengthOfWord = lengthOfWord,
                        directionOfWord = directionOfWord)

                if (isSizedCorrectly)
                {
                    isPositionedCorrectly = checkIsPositionedCorrectly(word = word.word,
                            startIndexOfWord = startIndexOfWord,
                            lengthOfWord = lengthOfWord,
                            directionOfWord = directionOfWord)

                    if (isPositionedCorrectly)
                    {
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
                    startIndexOfWord = determineStartIndex(lengthOfWord = lengthOfWord)
                    directionOfWord = Random.nextInt(AppConstants.NUMBER_OF_DIRECTIONS)
                    count = 0
                }
            }

            changeLetterArray(startIndexOfWord = startIndexOfWord,
                    lengthOfWord = lengthOfWord,
                    directionOfWord = directionOfWord,
                    word = word.word)
        }
    }


    fun setupUI()
    {
        val letterAdapter = LetterAdapter(context = context,
                letters = gridViewModel.getLetters())

        gridHandler.setLetters(context = context,
                letterAdapter = letterAdapter,
                gridView = gridView)

        gridHandler.setTableLayout(context = context,
                keySet = gridViewModel.getWords(),
                tableLayout = wordTableLayout)

        gridHandler.setOnTouchListener(context = context,
                onTouchListener = drawAdapter.getOnTouchListener(),
                gridView = gridView)

        informationBarViewModel.setTotalWords(totalWords = gridViewModel.getWords().size)
    }
}
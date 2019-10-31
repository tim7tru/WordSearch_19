package com.example.timmytruong.wordsearch_19.utils.constant

import android.content.res.Resources
import com.example.timmytruong.wordsearch_19.R

object AppConstants
{
     val DEFAULT_WORDS = arrayOf("KOTLIN", "SWIFT", "OBJECTIVEC", "MOBILE", "JAVA", "VARIABLE")

     val INTENT_EXTRA_WORDS_ARRAY_LIST_KEY = "words"

     val UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

     val NUMBER_OF_CELLS = 100

     val NUMBER_OF_DIRECTIONS = 8

     val DIRECTION_STRAIGHT_RIGHT = 0

     val DIRECTION_DIAGONAL_DOWN_RIGHT = 1

     val DIRECTION_STRAIGHT_DOWN = 2

     val DIRECTION_DIAGONAL_DOWN_LEFT = 3

     val DIRECTION_STRAIGHT_LEFT = 4

     val DIRECTION_DIAGONAL_UP_LEFT = 5

     val DIRECTION_STRAIGHT_UP = 6

     val DIRECTION_DIAGONAL_UP_RIGHT = 7

     val PAINT_LINE_WIDTH_SEARCH = 30.0f

     val PAINT_LINE_WIDTH_FOUND = 55.0f
}
package com.example.timmytruong.wordsearch_19.utils.constant

import android.graphics.Color

object AppConstants
{
     val DEFAULT_WORDS = arrayOf("OBJECTIVEC", "VARIABLE", "KOTLIN", "MOBILE", "SWIFT", "JAVA")

     val EDGE_CELLS = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 19, 29, 39, 49, 59, 69, 79, 89, 99, 91, 92, 93, 94, 95, 96, 97, 98)

     const val INTENT_EXTRA_WORDS_ARRAY_LIST_KEY = "words"

     const val UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

     const val NUMBER_OF_CELLS = 100

     const val NUMBER_OF_DIRECTIONS = 8

     const val DIRECTION_STRAIGHT_RIGHT = 0

     const val DIRECTION_DIAGONAL_DOWN_RIGHT = 1

     const val DIRECTION_STRAIGHT_DOWN = 2

     const val DIRECTION_DIAGONAL_DOWN_LEFT = 3

     const val DIRECTION_STRAIGHT_LEFT = 4

     const val DIRECTION_DIAGONAL_UP_LEFT = 5

     const val DIRECTION_STRAIGHT_UP = 6

     const val DIRECTION_DIAGONAL_UP_RIGHT = 7

     const val PAINT_LINE_WIDTH_SEARCH = 30.0f

     const val PAINT_LINE_WIDTH_FOUND = 55.0f

     const val PAINT_COLOUR_YELLOW = Color.YELLOW

     const val PAINT_COLOUR_GREEN = Color.GREEN

     const val FRAGMENT_EDIT_WORDS = "edit_words"
}
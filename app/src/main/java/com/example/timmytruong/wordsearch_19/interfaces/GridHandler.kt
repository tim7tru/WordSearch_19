package com.example.timmytruong.wordsearch_19.interfaces

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TableLayout
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter

interface GridHandler
{
    fun strikeOutWord(context: Context, word: String, tableLayout: TableLayout)

    fun setOnTouchListener(context: Context, onTouchListener: View.OnTouchListener,
                           gridView: GridView)

    fun setLetters(context: Context, letterAdapter: LetterAdapter, gridView: GridView)

    fun setTableLayout(context: Context, keySet: Set<String>, tableLayout: TableLayout)

    fun clearTableLayout(context: Context, tableLayout: TableLayout)

    fun removeSearchView(context: Context, startViewCount: Int, endViewCount: Int,
                         gridFrame: FrameLayout)

    fun displayWinDialogue(context: Context)

    fun getLetterAdapter(context: Context, letters: LinkedHashMap<Int, Char>): LetterAdapter
}
package com.example.timmytruong.wordsearch_19.interfaces

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView

interface InformationBarHandler
{
    fun setResetClickListener(onClickListener: View.OnClickListener, resetBTN: Button)

    fun setScoreTextView(score: Int, total: Int, scoreView: TextView)

    fun setPlusClickListener(context: Context, words: LinkedHashMap<String, Boolean>, plusBTN: Button)
}
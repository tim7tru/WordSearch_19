package com.example.timmytruong.wordsearch_19.interfaces

import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.model.Word

interface InformationBarHandler
{
    fun setResetClickListener(resetBTN: Button)

    fun setScoreTextView(score: Int, total: Int, scoreView: TextView)

    fun setPlusClickListener(context: Context, words: ArrayList<Word>, plusBTN: Button)
}
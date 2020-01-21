package com.example.timmytruong.wordsearch_19.interfaces

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.model.Word

interface InformationBarHandler
{
    fun setResetClickListener(resetBTN: TextView)

    fun setScoreTextView(score: Int, total: Int, scoreView: TextView)

    fun setSettingsClickListener(context: Context, words: ArrayList<Word>, settingsBTN: ImageView)
}
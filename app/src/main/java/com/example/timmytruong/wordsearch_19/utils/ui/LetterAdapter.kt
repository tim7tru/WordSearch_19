package com.example.timmytruong.wordsearch_19.utils.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.model.Letter

class LetterAdapter(val context: Context, private val letters: ArrayList<Letter>) :
        BaseAdapter()
{
    override fun getCount(): Int
    {
        return letters.size
    }

    override fun getItem(position: Int): Any
    {
        return position
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        var newConvertView = convertView
        if (convertView == null)
        {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            newConvertView = layoutInflater.inflate(R.layout.letter_layout, null)
        }

        val letter: TextView = newConvertView!!.findViewById(R.id.letter)
        letter.tag = position
        letter.text = letters[position].letter.toString()

        return newConvertView
    }
}
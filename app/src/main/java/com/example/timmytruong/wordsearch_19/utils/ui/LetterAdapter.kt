package com.example.timmytruong.wordsearch_19.utils.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.R

class LetterAdapter(context: Context, letters: LinkedHashMap<Int, Char>) : BaseAdapter()
{

    val letters: LinkedHashMap<Int, Char>

    val context: Context

    init
    {
        this.context = context
        this.letters = letters
    }

    override fun getCount(): Int
    {
        return letters.keys.size
    }

    override fun getItem(position: Int): Any
    {
        return position
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        var newConvertView: View? = convertView
        if (convertView == null)
        {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            newConvertView = layoutInflater.inflate(R.layout.letter_layout, null)
        }

        val letter: TextView = newConvertView!!.findViewById(R.id.letter)
        letter.tag = position
        letter.text = letters.get(position).toString()

        return newConvertView
    }
}
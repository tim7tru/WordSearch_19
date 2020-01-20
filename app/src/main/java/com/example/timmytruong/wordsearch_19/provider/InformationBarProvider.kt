package com.example.timmytruong.wordsearch_19.provider

import javax.inject.Inject

class InformationBarProvider @Inject constructor()
{
    private var score: Int = 0

    private var totalWords: Int = 0

    fun setScore(resetScore: Boolean)
    {
        when (resetScore)
        {
            true ->
            {
                score = 0
            }
            false ->
            {
                score++
            }
        }
    }

    fun setTotalWords(totalCount: Int)
    {
        totalWords = totalCount
    }

    fun getTotalWords(): Int
    {
        return totalWords
    }

    fun getScore(): Int
    {
        return score
    }
}
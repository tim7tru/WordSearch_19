package com.example.timmytruong.wordsearch_19.models

class InformationBarModel
{
    private var score: Int = 0

    private var totalWords: Int = 0

    fun setScore(score: Int)
    {
        this.score = score
    }

    fun getScore(): Int
    {
        return score
    }

    fun setTotalWords(totalWords: Int)
    {
        this.totalWords = totalWords
    }

    fun getTotalWords(): Int
    {
        return totalWords
    }
}
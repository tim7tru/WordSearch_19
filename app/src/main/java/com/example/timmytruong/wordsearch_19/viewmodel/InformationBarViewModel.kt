package com.example.timmytruong.wordsearch_19.viewmodel

import com.example.timmytruong.wordsearch_19.provider.InformationBarProvider

class InformationBarViewModel {

    private val informationBarProvider = InformationBarProvider()

    fun setScore(add: Boolean)
    {
        informationBarProvider.setScore(add)
    }

}
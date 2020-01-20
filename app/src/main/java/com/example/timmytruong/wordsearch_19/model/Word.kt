package com.example.timmytruong.wordsearch_19.model

import java.io.Serializable

data class Word(var index: Int,
                var word: String,
                var beenFound: Boolean) : Serializable
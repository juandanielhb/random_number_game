package com.jdhb.game.services

import com.jdhb.game.controller.dtos.Bet

interface GameStrategy {

    val randomRangeStart: Int
    val randomRangeEnd: Int
    fun validateBets(playerBets: List<Bet>): List<Bet>
    fun getResults(playerBets: List<Bet>): List<Bet>
    fun generatedNumber(): Int
    fun getStrategy(): String
}
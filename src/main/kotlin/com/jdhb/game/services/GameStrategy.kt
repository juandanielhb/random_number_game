package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO

interface GameStrategy {

    val randomRangeStart: Int
    val randomRangeEnd: Int
    fun validateBets(bets: List<BetDTO>): List<BetDTO>
    fun getResults(bets: List<BetDTO>): List<BetDTO>
    fun generatedNumber(): Int
    fun getStrategy(): String
}
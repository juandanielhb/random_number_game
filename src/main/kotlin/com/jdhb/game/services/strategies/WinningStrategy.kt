package com.jdhb.game.services.strategies

interface WinningStrategy {
    fun calculateWinnings(betAmount: Int, playerNumber: Int, generatedNumber: Int): Int
}
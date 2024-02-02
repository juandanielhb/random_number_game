package com.jdhb.game.services.strategies

class ExactMatchStrategy : WinningStrategy {
    override fun calculateWinnings(betAmount: Int, playerNumber: Int, generatedNumber: Int): Int {
        return if (playerNumber == generatedNumber) betAmount * 10 else 0
    }
}
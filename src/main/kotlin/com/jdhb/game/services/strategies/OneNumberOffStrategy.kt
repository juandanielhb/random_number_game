package com.jdhb.game.services.strategies

class OneNumberOffStrategy : WinningStrategy {
    override fun calculateWinnings(betAmount: Int, playerNumber: Int, generatedNumber: Int): Int {
        return if (playerNumber == generatedNumber - 1 || playerNumber == generatedNumber + 1) betAmount * 5 else 0
    }
}
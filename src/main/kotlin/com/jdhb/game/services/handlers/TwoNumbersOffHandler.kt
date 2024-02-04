package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.controller.dtos.updateResult
import com.jdhb.game.entities.enums.BetResults
import org.springframework.stereotype.Component

@Component
class TwoNumbersOffHandler() : BetHandler {
    private var nextHandler: BetHandler? = null
    private val multiplier = 0.5

    override fun handleBet(bet: Bet): Bet {
        if (bet.selectedNumber in (bet.generatedNumber - 2)..(bet.generatedNumber + 2)) {
            return bet.updateResult(multiplier, BetResults.WIN, bet.betAmount * multiplier)
        } else {
            return nextHandler?.handleBet(bet) ?: bet
        }
    }

    override fun setNextHandler(handler: BetHandler) {
        nextHandler = handler
    }
}
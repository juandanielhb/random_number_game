package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.controller.dtos.updateResult
import com.jdhb.game.entities.enums.BetResults
import org.springframework.stereotype.Component

@Component
class OneNumberOffHandler() : BetHandler {
    private var nextHandler: BetHandler? = null
    private val multiplier = 10.0

    override fun handleBet(bet: Bet): Bet {
        if (bet.selectedNumber in (bet.generatedNumber - 1)..(bet.generatedNumber + 1)) {
            return bet.updateResult(multiplier, BetResults.WIN, bet.betAmount * multiplier)
        } else {
            return nextHandler?.handleBet(bet) ?: bet
        }
    }

    override fun setNextHandler(handler: BetHandler) {
        nextHandler = handler
    }
}
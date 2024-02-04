package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.controller.dtos.updateResult
import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.enums.BetResults
import org.springframework.stereotype.Component

@Component
class ExactMatchHandler : BetHandler {
    private var nextHandler: BetHandler? = null
    private val multiplier = 10.0

    override fun handleBet(bet: Bet): Bet {
        if (bet.selectedNumber == bet.generatedNumber) {
            return bet.updateResult(multiplier, BetResults.WIN, bet.betAmount * multiplier)
        } else {
            return nextHandler?.handleBet(bet) ?: bet
        }
    }

    override fun setNextHandler(handler: BetHandler) {
        nextHandler = handler
    }
}

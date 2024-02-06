package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.updateResult
import com.jdhb.game.entities.enums.BetResults
import org.springframework.stereotype.Component

private const val MULTIPLIER = 10.0

@Component
class ExactMatchHandler : BetHandler {
    private var nextHandler: BetHandler? = null

    override fun handleBet(bet: BetDTO): BetDTO {
        if (bet.selectedNumber == bet.generatedNumber) {
            return bet.updateResult(MULTIPLIER, BetResults.WIN, bet.betAmount * MULTIPLIER)
        } else {
            return nextHandler?.handleBet(bet) ?: bet
        }
    }

    override fun setNextHandler(handler: BetHandler) {
        nextHandler = handler
    }
}

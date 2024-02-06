package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.updateResult
import com.jdhb.game.entities.enums.BetResults
import org.springframework.stereotype.Component

private const val MULTIPLIER = 0.5

@Component
class TwoNumbersOffHandler() : BetHandler {
    private var nextHandler: BetHandler? = null


    override fun handleBet(bet: BetDTO): BetDTO {
        if (bet.selectedNumber in (bet.generatedNumber - 2)..(bet.generatedNumber + 2)) {
            return bet.updateResult(MULTIPLIER, BetResults.WIN, bet.betAmount * MULTIPLIER)
        } else {
            return nextHandler?.handleBet(bet) ?: bet
        }
    }

    override fun setNextHandler(handler: BetHandler) {
        nextHandler = handler
    }
}
package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet
import org.springframework.stereotype.Service

@Service
class StandardBetHandlerChain(
    private val exactMatchHandler: ExactMatchHandler,
    private val oneNumberOffHandler: OneNumberOffHandler,
    private val twoNumbersOffHandler: TwoNumbersOffHandler
): BetHandlerChain {

    init {
        exactMatchHandler.setNextHandler(oneNumberOffHandler)
        oneNumberOffHandler.setNextHandler(twoNumbersOffHandler)
    }

    override fun handleBet(bet: Bet): Bet? {
        return exactMatchHandler.handleBet(bet)
    }
}
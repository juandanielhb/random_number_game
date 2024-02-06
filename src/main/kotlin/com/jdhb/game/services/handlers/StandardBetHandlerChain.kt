package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO
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

    override fun handleBet(bet: BetDTO): BetDTO? {
        return exactMatchHandler.handleBet(bet)
    }
}
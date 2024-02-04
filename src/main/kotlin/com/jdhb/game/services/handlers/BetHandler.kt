package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet

interface BetHandler {
    fun handleBet(bet: Bet): Bet
    fun setNextHandler(handler: BetHandler)

}
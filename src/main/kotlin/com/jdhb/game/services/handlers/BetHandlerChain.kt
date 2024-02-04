package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.Bet

interface BetHandlerChain {
    fun handleBet(bet: Bet): Bet?
}
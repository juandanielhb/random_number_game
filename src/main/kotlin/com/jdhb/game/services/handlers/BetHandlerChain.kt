package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO

interface BetHandlerChain {
    fun handleBet(bet: BetDTO): BetDTO?
}
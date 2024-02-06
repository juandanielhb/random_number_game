package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO

interface BetHandler {
    fun handleBet(bet: BetDTO): BetDTO
    fun setNextHandler(handler: BetHandler)

}
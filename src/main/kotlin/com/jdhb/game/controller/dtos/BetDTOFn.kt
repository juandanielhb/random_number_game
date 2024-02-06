package com.jdhb.game.controller.dtos

import com.jdhb.game.entities.enums.BetResults

fun BetDTO.updateResult(multiplier: Double, result: BetResults, winnings: Double) = this.apply {
    this.multiplier = multiplier
    this.result = result
    this.winnings = winnings
}

package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.GameStrategies
import com.jdhb.game.services.handlers.StandardBetHandlerChain
import org.springframework.stereotype.Service

@Service
class StandardGameStrategy(
    private val betHandlerChain: StandardBetHandlerChain,
    private val betService: BetService,
) : GameStrategy {
    override val randomRangeStart: Int = 1
    override val randomRangeEnd: Int = 10

    override fun validateBets(bets: List<BetDTO>): List<BetDTO>{
        betService.checkSingleBetPerPlayer(bets)
        bets.forEach {
            betService.validateBet(it, randomRangeStart, randomRangeEnd)
        }
        return bets
    }

    override fun getResults(bets: List<BetDTO>): List<BetDTO> {
        val generatedNumber = generatedNumber()
        bets.forEach {
            it.generatedNumber = generatedNumber
            betHandlerChain.handleBet(it)
        }
        return bets
    }

    override fun generatedNumber(): Int {
        return (randomRangeStart..randomRangeEnd).random()
    }

    override fun getStrategy(): String {
        return GameStrategies.STANDARD.value
    }

}

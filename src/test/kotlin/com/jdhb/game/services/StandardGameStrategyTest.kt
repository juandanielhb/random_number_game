package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.GameStrategies
import com.jdhb.game.services.handlers.StandardBetHandlerChain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StandardGameStrategyTest{
    @Mock
    private lateinit var betHandlerChain: StandardBetHandlerChain
    @Mock
    private lateinit var playerService: PlayerService
    @Mock
    private lateinit var betService: BetService
    @Mock
    private lateinit var ex: BetService

    @InjectMocks
    private lateinit var standardGameStrategy: StandardGameStrategy

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGeneratedNumber() {
        val randomRangeStart = standardGameStrategy.randomRangeStart
        val randomRangeEnd = standardGameStrategy.randomRangeEnd

        val result = standardGameStrategy.generatedNumber()

        assertEquals(true, result in randomRangeStart..randomRangeEnd)
    }

    @Test
    fun testGetStrategy() {
        val expectedStrategy = GameStrategies.STANDARD.value

        val result = standardGameStrategy.getStrategy()

        assertEquals(expectedStrategy, result)
    }

    @Test
    fun testValidateBets() {
        val bets = listOf(
            BetDTO(playerId = 1, betAmount = 5.0, selectedNumber = 5),
            BetDTO(playerId = 2, betAmount = 10.0, selectedNumber = 5)
        )

        `when`(betService.checkSingleBetPerPlayer(bets)).thenCallRealMethod()

        bets.forEach {
            `when`(betService.validateBet(it, standardGameStrategy.randomRangeStart, standardGameStrategy.randomRangeEnd))
                .thenCallRealMethod()
        }

        val result = standardGameStrategy.validateBets(bets)

        assertEquals(bets, result)
    }

}
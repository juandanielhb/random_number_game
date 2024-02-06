package com.jdhb.game.services.handlers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.BetResults
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ExactMatchHandlerTest {

    @Mock
    private lateinit var nextHandler: BetHandler

    @InjectMocks
    private lateinit var exactMatchHandler: ExactMatchHandler

    @Test
    fun handleBetShouldUpdateResultAndWinningsWhenSelectedNumberIsAnExactMatch() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 5)

        val resultBet = exactMatchHandler.handleBet(bet)

        assertEquals(BetResults.WIN, resultBet.result)
        assertEquals(100.0, resultBet.winnings)
        assertEquals(10.0, resultBet.multiplier)
    }

    @Test
    fun handleBetShouldDelegateToTheNextHandlerWhenSelectedNumberIsNotExactMatch() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10)

        exactMatchHandler.handleBet(bet)

        Mockito.verify(nextHandler, Mockito.times(1)).handleBet(bet)

    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNextHandler() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 5)

        val resultBet = exactMatchHandler.handleBet(bet)

        assertSame(bet, resultBet)
    }
}
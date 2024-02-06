package com.jdhb.game.services.handlers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.BetResults
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OneNumberOffHandlerTest {

    @Mock
    private lateinit var nextHandler: BetHandler

    @InjectMocks
    private lateinit var oneNumberOffHandler: OneNumberOffHandler

    @Test
    fun handleBetShouldUpdateResultAndWinningsWhenSelectedNumberIsOneNumberOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 6)

        val resultBet = oneNumberOffHandler.handleBet(bet)

        assertEquals(BetResults.WIN, resultBet.result)
        assertEquals(100.0, resultBet.winnings)
        assertEquals(10.0, resultBet.multiplier)
    }

    @Test
    fun handleBetShouldDelegateToTheNextHandlerWhenSelectedNumberIsNotOneNumberOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10)

        oneNumberOffHandler.handleBet(bet)

        verify(nextHandler, times(1)).handleBet(bet)
    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNextHandler() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10)

        val resultBet = oneNumberOffHandler.handleBet(bet)

        assertEquals(bet, resultBet)
    }
}
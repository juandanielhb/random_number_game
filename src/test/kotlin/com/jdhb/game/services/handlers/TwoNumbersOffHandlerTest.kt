package com.jdhb.game.services.handlers

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.BetResults
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TwoNumbersOffHandlerTest {

    @Mock
    private lateinit var nextHandler: BetHandler

    @InjectMocks
    private lateinit var twoNumbersOffHandler: TwoNumbersOffHandler

    @Test
    fun handleBetShouldUpdateResultAndWinningsWhenSelectedNumberIsTwoNumbersOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 7)

        val resultBet = twoNumbersOffHandler.handleBet(bet)

        assertEquals(BetResults.WIN, resultBet.result)
        assertEquals(5.0, resultBet.winnings)
        assertEquals(0.5, resultBet.multiplier)
    }

    @Test
    fun handleBetShouldDelegateToTheNextHandlerWhenSelectedNumberIsNotTwoNumberOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10)

        twoNumbersOffHandler.handleBet(bet)

        verify(nextHandler, Mockito.times(1)).handleBet(bet)
    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNextHandler() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10)

        val resultBet = twoNumbersOffHandler.handleBet(bet)

        assertEquals(bet, resultBet)
    }
}
package com.jdhb.game.services.handlers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.BetResults
import io.mockk.every
import io.mockk.mockk

class StandardBetHandlerChainTest {

    private val exactMatchHandler: ExactMatchHandler = mockk(relaxed = true)
    private val oneNumberOffHandler: OneNumberOffHandler = mockk(relaxed = true)
    private val twoNumbersOffHandler: TwoNumbersOffHandler = mockk(relaxed = true)

    private val standardBetHandlerChain = StandardBetHandlerChain(
        exactMatchHandler,
        oneNumberOffHandler,
        twoNumbersOffHandler
    )

    @Test
    fun handleBetShouldReturnUpdatedBetDTOWhenExactMatchHandlerHandlesTheBet() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 5)
        every { exactMatchHandler.handleBet(bet) } answers { callOriginal() }

        val result = standardBetHandlerChain.handleBet(bet)

        assertEquals(bet, result)
        assertEquals(BetResults.WIN, result?.result)
        assertEquals(100.0, result?.winnings)
    }

    @Test
    fun handleBetShouldReturnUpdatedBetDTOWhenOneNumberOffHandlerHandlesTheBet() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 6)
        every { exactMatchHandler.handleBet(bet) } returns bet
        every { oneNumberOffHandler.handleBet(bet) } returns bet

        val result = standardBetHandlerChain.handleBet(bet)

        assertEquals(bet, result)
    }

    @Test
    fun handleBetShouldReturnUpdatedBetDTOWhenTwoNumbersOffHandlerHandlesTheBet() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 7)
        every { exactMatchHandler.handleBet(bet) } returns bet
        every { oneNumberOffHandler.handleBet(bet) } returns bet
        every { twoNumbersOffHandler.handleBet(bet) } returns bet

        val result = standardBetHandlerChain.handleBet(bet)

        assertEquals(bet, result)
    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNexthandlerForExactmatch() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 5)
        every { exactMatchHandler.handleBet(bet) } returns bet

        val result = standardBetHandlerChain.handleBet(bet)

        assertSame(bet, result)
    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNextHandlerForOneNumberOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 6)
        every { exactMatchHandler.handleBet(bet) } returns bet
        every { oneNumberOffHandler.handleBet(bet) } returns bet
        every { twoNumbersOffHandler.handleBet(bet) } returns bet

        val result = standardBetHandlerChain.handleBet(bet)

        assertSame(bet, result)
    }

    @Test
    fun handleBetShouldReturnTheOriginalBetWhenThereIsNoNextHandlerForTwoNumbersOff() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 7)
        every { exactMatchHandler.handleBet(bet) } returns bet
        every { oneNumberOffHandler.handleBet(bet) } returns bet
        every { twoNumbersOffHandler.handleBet(bet) } returns bet

        val result = standardBetHandlerChain.handleBet(bet)

        assertSame(bet, result)
    }
}
package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.exceptions.BetSelectedNumberInvalidException
import com.jdhb.game.exceptions.InvalidBetException
import com.jdhb.game.exceptions.PlayerInvalidException
import com.jdhb.game.mappers.BetMapper
import com.jdhb.game.repositories.BetRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

@ExtendWith(MockitoExtension::class)
class BetServiceTest {

    @Mock
    private lateinit var betRepository: BetRepository

    @Mock
    private lateinit var betMapper: BetMapper

    @Mock
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var walletTransactionService: WalletTransactionService

    @InjectMocks
    private lateinit var betService: BetService

    @Test
    fun testGetBetsByPlayerId() {
        val playerId = 1L
        val pageable: Pageable = mockk()
        val playerEntity1 = PlayerEntity(id = 1, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val betEntityList = listOf(BetEntity(player = playerEntity1, betAmount = 10.0, selectedNumber = 5, generatedNumber = 5, result = BetResults.WIN))
        val betDTOList = listOf(BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5))

        `when`(betRepository.findByPlayerId(playerId, pageable)).thenReturn(PageImpl(betEntityList))
        `when`(betMapper.toDto(betEntityList)).thenReturn(betDTOList)

        val result = betService.getBetsByPlayerId(playerId, pageable)

        assertEquals(betDTOList, result)
    }

    @Test
    fun validateBetShouldThrowInvalidBetExceptionWhenValidationFails() {
        val bet = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 15)
        val playerEntity1 = PlayerEntity(id = 1, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val rangeStart = 1
        val rangeEnd = 10

        `when`(playerService.validatePlayer(playerEntity1.id!!)).thenThrow(PlayerInvalidException("Player was not found"))

        assertThrows<InvalidBetException> {
            betService.validateBet(bet, rangeStart, rangeEnd)
        }
    }

    @Test
    fun checkSingleBetPerPlayerShouldThrowInvalidBetExceptionWhenMultipleBetsPerPlayer() {
        val bet1 = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5)
        val bet2 = BetDTO(playerId = 1L, betAmount = 20.0, selectedNumber = 8)
        val bets = listOf(bet1, bet2)

        assertThrows<InvalidBetException> {
            betService.checkSingleBetPerPlayer(bets)
        }
    }

    @Test
    fun saveBetShouldSaveAndReturnAListOfBetEntity() {
        val bet1 = BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5)
        val bet2 = BetDTO(playerId = 2L, betAmount = 20.0, selectedNumber = 8)
        val playerEntity1 = PlayerEntity(id = 1, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val playerEntity2 = PlayerEntity(id = 2, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val bets = listOf(bet1, bet2)
        val betEntities = listOf(BetEntity(player = playerEntity1, betAmount = 10.0, selectedNumber = 5, generatedNumber = 10, result = BetResults.LOSE),
            BetEntity(player = playerEntity2, betAmount = 20.0, selectedNumber = 8, generatedNumber = 10, result = BetResults.WIN))

        `when`(betMapper.toEntity(bets)).thenReturn(betEntities)
        `when`(betRepository.saveAll(betEntities)).thenReturn(betEntities)

        val result = betService.saveBets(bets)

        assertEquals(betEntities, result)
    }

    @Test
    fun validateBetSelectedNumberShouldThrowBetSelectedNumberInvalidExceptionWhenSelectedNumberIsOutsideTheRange() {
        val selectedNumber = 15
        val rangeStart = 1
        val rangeEnd = 10

        assertThrows<BetSelectedNumberInvalidException> {
            betService.validateBetSelectedNumber(selectedNumber, rangeStart, rangeEnd)
        }
    }

    @Test
    fun validateBetSelectedNumberShouldNotThrowExceptionWhenSelectedNumberIsWithinTheRange() {
        val selectedNumber = 5
        val rangeStart = 1
        val rangeEnd = 10

        betService.validateBetSelectedNumber(selectedNumber, rangeStart, rangeEnd)
    }
}

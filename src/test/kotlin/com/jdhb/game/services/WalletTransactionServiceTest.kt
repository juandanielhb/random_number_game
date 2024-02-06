package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.WalletTransactionDTO
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.mappers.WalletTransactionMapper
import com.jdhb.game.repositories.WalletTransactionRepository
import jakarta.persistence.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

class WalletTransactionServiceTest {

    @Mock
    private lateinit var walletTransactionRepository: WalletTransactionRepository

    @Mock
    private lateinit var walletTransactionMapper: WalletTransactionMapper

    @InjectMocks
    private lateinit var walletTransactionService: WalletTransactionService

    private lateinit var player: PlayerEntity
    private lateinit var now: LocalDateTime
    private lateinit var playerEntity: PlayerEntity
    private lateinit var bet: BetDTO


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        now = LocalDateTime.now()
        player = PlayerEntity(
            id = 1,
            name = "",
            surname = "",
            username = "winner1",
            wallet = WalletEntity(1, ),
            createdAt = now,
            updatedAt = now,
        )

        playerEntity = PlayerEntity(
            id = 1,
            name = "",
            surname = "",
            username = "winner1",
            wallet = WalletEntity(1, 990.0),
            createdAt = now,
            updatedAt = now,
            )

        bet = BetDTO(
            playerId = player.id!!,
            betAmount = 10.0,
            selectedNumber = 5,
            generatedNumber = 1,
            multiplier = 0.0,
            winnings = 0.0,
            timestamp = now,
        )
    }

    @Test
    fun testGetAllTransactionsByUser() {
        val playerId = 1L
        val pageable = PageRequest.of(0, 10)
        val walletTransactionEntities = listOf(
            WalletTransactionEntity(
                id = 1,
                transactionType = TransactionType.BET,
                amount = 10.0,
                walletBalance = 1010.0,
                timestamp = now,
                player = player
            )


        )
        val walletTransactionDTOs = listOf(
            WalletTransactionDTO(
                id = 1,
                transactionType = TransactionType.BET,
                playerId = player.id!!,
                amount = 10.0,
                walletBalance = 1010.0,
                timestamp = now,
            )
        )

        `when`(walletTransactionRepository.findByPlayerId(playerId, pageable))
            .thenReturn(PageImpl(walletTransactionEntities))

        `when`(walletTransactionMapper.toDto(walletTransactionEntities))
            .thenReturn(walletTransactionDTOs)

        val result = walletTransactionService.getAllTransactionsByUser(playerId, pageable)

        assertEquals(walletTransactionDTOs, result)
    }

    @Test
    fun testSave() {
        val walletTransactionEntity = WalletTransactionEntity(
            id = 1,
            transactionType = TransactionType.BET,
            amount = 10.0,
            walletBalance = 1010.0,
            timestamp = now,
            player = player
        )

        `when`(walletTransactionRepository.save(walletTransactionEntity))
            .thenReturn(walletTransactionEntity)

        val result = walletTransactionService.save(walletTransactionEntity)

        assertEquals(walletTransactionEntity, result)
    }

    @Test
    fun testSaveAll() {
        val walletTransactionEntities = mutableListOf(
            WalletTransactionEntity(
                id = 1,
                transactionType = TransactionType.BET,
                amount = 10.0,
                walletBalance = 1010.0,
                timestamp = now,
                player = player
            )
        )

        `when`(walletTransactionRepository.saveAll(walletTransactionEntities))
            .thenReturn(walletTransactionEntities)

        val result = walletTransactionService.saveAll(walletTransactionEntities)

        assertEquals(walletTransactionEntities, result)

    }

    @Test
    fun testProcessBetTransaction() {
        val result = walletTransactionService.processBetTransaction(player, bet)

        assertEquals(TransactionType.BET, result.transactionType)
        assertEquals(-bet.betAmount, result.amount)
        assertEquals(playerEntity, result.player)
        assertEquals(playerEntity.wallet.balance, result.walletBalance)
    }

    @Test
    fun testProcessWinTransaction() {
        bet.result = BetResults.WIN
        bet.multiplier = 5.0
        bet.winnings = 50.0
        playerEntity.wallet.balance = 1050.0

        val result = walletTransactionService.processWinTransaction(player, bet)

        assertEquals(TransactionType.WIN, result!!.transactionType)
        assertEquals(bet.winnings, result.amount)
        assertEquals(playerEntity, result.player)
        assertEquals(playerEntity.wallet.balance, result.walletBalance)
    }
}
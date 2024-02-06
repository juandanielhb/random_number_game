package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.PlayerDTO
import com.jdhb.game.controller.dtos.WalletTransactionDTO
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import com.jdhb.game.services.BetService
import com.jdhb.game.services.PlayerService
import com.jdhb.game.services.WalletTransactionService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate

class PlayerControllerTest {
    @Mock
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var betService: BetService

    @Mock
    private lateinit var walletTransactionService: WalletTransactionService

    @InjectMocks
    private lateinit var playerController: PlayerController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testRegisterThePlayerMethod() {
        val playerDTO = PlayerDTO(1, username = "testUser", name = "test", surname = "User")

        Mockito.`when`(playerService.register(playerDTO)).thenReturn(playerDTO)

        val responseEntity: ResponseEntity<PlayerDTO> = playerController.register(playerDTO)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == playerDTO)
    }

    @Test
    fun testGetAllTransactionsByUser() {
        val playerId = 1L
        val page = 0
        val size = 10
        val startDate: LocalDate? = null
        val endDate: LocalDate? = null
        val pageable = PageRequest.of(page, size)

        val walletTransactions = listOf(
            WalletTransactionDTO(id = 1, playerId = playerId, amount = 100.0),
            WalletTransactionDTO(id = 2, playerId = playerId, amount = -50.0)
        )

        Mockito.`when`(walletTransactionService.getAllTransactionsByUser(playerId, pageable))
            .thenReturn(walletTransactions)

        val responseEntity: ResponseEntity<List<WalletTransactionDTO>> =
            playerController.getAllTransactionsByUser(playerId, page, size, startDate, endDate)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == walletTransactions)
    }

    @Test
    fun testGetAllBetsByUser() {
        val playerId = 1L
        val page = 0
        val size = 10

        val bets = listOf(
            BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 6),
            BetDTO(playerId = 1L, betAmount = 10.0, selectedNumber = 5, generatedNumber = 6)
        )

        Mockito.`when`(betService.getBetsByPlayerId(playerId, PageRequest.of(page, size)))
            .thenReturn(bets)

        val responseEntity: ResponseEntity<List<BetDTO>> =
            playerController.getAllBetsByUser(playerId, page, size)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == bets)
    }

    @Test
    fun testGetPlayer() {
        val playerId = 1L
        val playerEntity1 = PlayerEntity(id = playerId, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())

        Mockito.`when`(playerService.getPlayer(playerId)).thenReturn(playerEntity1)

        val responseEntity: PlayerEntity = playerController.getPlayer(playerId)

        assert(responseEntity == playerEntity1)
    }
}
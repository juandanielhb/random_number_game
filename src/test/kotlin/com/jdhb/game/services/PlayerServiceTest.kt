package com.jdhb.game.services

import com.jdhb.game.controller.dtos.PlayerDTO
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import com.jdhb.game.exceptions.PlayerBalanceInvalidException
import com.jdhb.game.exceptions.PlayerInvalidException
import com.jdhb.game.exceptions.ResourceNotFoundException
import com.jdhb.game.mappers.PlayerMapper
import com.jdhb.game.repositories.PlayerRepository
import org.mockito.Mockito.`when`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

class PlayerServiceTest{
    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var playerMapper: PlayerMapper

    @InjectMocks
    private lateinit var playerService: PlayerService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testRegister() {
        val playerDTO = PlayerDTO(null, name= "test", surname = "User", username = "testUser")
        val playerEntity = PlayerEntity(id = 1, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())

        `when`(playerMapper.toEntity(playerDTO)).thenReturn(playerEntity)
        `when`(playerRepository.save(any())).thenReturn(playerEntity)
        `when`(playerMapper.toDto(playerEntity)).thenReturn(playerDTO)

        val result = playerService.register(playerDTO)

        assertThat(result).isEqualTo(playerDTO)
        verify(playerMapper).toEntity(playerDTO)
        verify(playerRepository).save(playerEntity)
        verify(playerMapper).toDto(playerEntity)
    }

    @Test
    fun testRegisterDuplicateUsername() {
        val playerDTO = PlayerDTO(null, name= "test", surname = "User", username = "existingUser")

        `when`(playerMapper.toEntity(playerDTO)).thenReturn(PlayerEntity(id = 1, username = "existingUser", name = "test", surname = "test", wallet = WalletEntity()))
        `when`(playerRepository.save(any())).thenThrow(DataIntegrityViolationException("Duplicate entry"))

        assertThrows<PlayerInvalidException> {
            playerService.register(playerDTO)
        }
    }

    @Test
    fun testGetPlayer() {
        val playerId = 1L
        val playerEntity = PlayerEntity(id = 1, username = "existingUser", name = "test", surname = "test", wallet = WalletEntity())

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity))

        val result = playerService.getPlayer(playerId)

        assertThat(result).isEqualTo(playerEntity)
    }

    @Test
    fun testGetPlayerNotFound() {
        val playerId = 1L

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            playerService.getPlayer(playerId)
        }
    }

    @Test
    fun testValidatePlayerBalanceSufficientFunds() {
        val playerEntity = PlayerEntity(id = 1L, username = "existingUser", name = "test", surname = "test", wallet = WalletEntity(balance = 100.0))
        val amount = 50.0

        playerService.validatePlayerBalance(playerEntity, amount)
    }

    @Test
    fun testValidatePlayerBalanceInsufficientFunds() {
        val playerEntity = PlayerEntity(id = 1, username = "existingUser", name = "test", surname = "test", wallet = WalletEntity(balance = 30.0))
        val amount = 50.0

        assertThrows<PlayerBalanceInvalidException> {
            playerService.validatePlayerBalance(playerEntity, amount)
        }
    }

    @Test
    fun testValidatePlayerBalanceEmptyWallet() {
        val playerEntity = PlayerEntity(id = 1, username = "existingUser", name = "test", surname = "test", wallet = WalletEntity(balance = 0.0))
        val amount = 50.0

        assertThrows<PlayerBalanceInvalidException> {
            playerService.validatePlayerBalance(playerEntity, amount)
        }
    }
}
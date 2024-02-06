package com.jdhb.game.services

import com.jdhb.game.controller.dtos.PlayerDTO
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.exceptions.PlayerBalanceInvalidException
import com.jdhb.game.exceptions.PlayerInvalidException
import com.jdhb.game.exceptions.ResourceNotFoundException
import com.jdhb.game.mappers.PlayerMapper
import com.jdhb.game.repositories.PlayerRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

private val logger = KotlinLogging.logger {}
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerMapper: PlayerMapper,
) {
    fun register(playerDTO: PlayerDTO): PlayerDTO {
        try {
            logger.info { "Player to save $playerDTO" }
            return playerMapper.toDto(playerRepository.save(playerMapper.toEntity(playerDTO)));
        } catch (ex: DataIntegrityViolationException){
            throw PlayerInvalidException("Player username is already registered")
        }
    }

    fun getPlayer(id: Long): PlayerEntity = playerRepository.findById(id).getOrElse {
        throw ResourceNotFoundException("Player was not found")
    }

    fun validatePlayer(playerId: Long): PlayerEntity {
        val player: PlayerEntity
        try {
            player = getPlayer(playerId)
        } catch (ex: ResourceNotFoundException) {
            throw PlayerInvalidException("Player was not found")
        }
        return player
    }

    fun validatePlayerBalance(player: PlayerEntity, amount: Double) {
        val balance = player.wallet.balance

        if (balance < amount || balance == 0.0) {
            throw PlayerBalanceInvalidException("Insufficient funds for player ${player.id}")
        }
    }
}
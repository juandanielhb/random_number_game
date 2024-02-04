package com.jdhb.game.services

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.controller.dtos.Player
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.exceptions.PlayerBalanceInvalidException
import com.jdhb.game.exceptions.PlayerInvalidException
import com.jdhb.game.exceptions.ResourceNotFoundException
import com.jdhb.game.mappers.PlayerMapper
import com.jdhb.game.repositories.PlayerRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

private val logger = KotlinLogging.logger {}
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerMapper: PlayerMapper,
) {
    fun register(player: Player): Player {
        logger.info { "Player to save $player" }
        return playerMapper.toDto(playerRepository.save(playerMapper.toEntity(player)));
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
        return getPlayer(playerId)
    }

    fun validatePlayerBalance(player: PlayerEntity, amount: Double) {
        val balance = player.wallet.balance

        if (balance < amount || balance == 0.0) {
            throw PlayerBalanceInvalidException("Insufficient funds for player ${player.id}")
        }
    }

    @Transactional
    fun updatePlayersBalance(bets: List<Bet>): List<PlayerEntity> {
        TODO("Not yet implemented")
    }

}
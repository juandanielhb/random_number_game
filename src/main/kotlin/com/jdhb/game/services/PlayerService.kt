package com.jdhb.game.services

import com.jdhb.game.controller.dtos.PlayerDto
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.mappers.PlayerMapper
import com.jdhb.game.repositories.PlayerRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.Optional

private val logger = KotlinLogging.logger {}
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerMapper: PlayerMapper,
) {
    fun save(playerDTO: PlayerDto): PlayerDto {
        logger.info { "Player to save $playerDTO" }
        return playerMapper.toDto(playerRepository.save(playerMapper.toEntity(playerDTO)));
    }

    fun get(id: Long): Optional<PlayerEntity> = playerRepository.findById(id)

}
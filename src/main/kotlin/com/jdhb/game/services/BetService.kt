package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDto
import com.jdhb.game.mappers.BetMapper
import com.jdhb.game.mappers.PlayerMapper
import com.jdhb.game.repositories.BetRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class BetService(
    val betRepository: BetRepository,
    val betMapper: BetMapper,
    val playerMapper: PlayerMapper,
) {
    fun save(betDto: BetDto): BetDto {
        logger.info { "Bet to save $betDto" }
        val betEntity = betMapper.toEntity(betDto);
        betEntity.player = playerMapper.fromId(betDto.playerId)
        return betMapper.toDto(betRepository.save(betEntity));
    }
}
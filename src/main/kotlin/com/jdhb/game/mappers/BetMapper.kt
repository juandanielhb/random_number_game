package com.jdhb.game.mappers

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.entities.BetEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface BetMapper : EntityMapper<Bet, BetEntity>{
    @Mapping(target = "player", expression = "java(new PlayerEntity(dto.getPlayerId(), null, null, null, new com.jdhb.game.entities.WalletEntity(), null, null))")
    override fun toEntity(dto: Bet): BetEntity
}

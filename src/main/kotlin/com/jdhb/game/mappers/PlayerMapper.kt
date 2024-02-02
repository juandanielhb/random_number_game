package com.jdhb.game.mappers

import com.jdhb.game.controller.dtos.PlayerDto
import com.jdhb.game.entities.PlayerEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PlayerMapper : EntityMapper<PlayerDto, PlayerEntity>{

    fun fromId(id: Long?): PlayerEntity? {
        if (id == null) {
            return null
        }
        val playerEntity = PlayerEntity(id, null, null, null, null)
        playerEntity.id = id
        return playerEntity
    }
}
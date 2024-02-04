package com.jdhb.game.mappers

import com.jdhb.game.controller.dtos.Player
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.text.SimpleDateFormat
import java.util.*


@Mapper(componentModel = "spring")
interface PlayerMapper : EntityMapper<Player, PlayerEntity>{
    fun fromId(id: Long?): PlayerEntity {
        return PlayerEntity(id, null, null, null, WalletEntity(null))
    }

    @Mapping(target = "wallet", expression = "java(new WalletEntity())")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    override fun toEntity(dto: Player): PlayerEntity

}
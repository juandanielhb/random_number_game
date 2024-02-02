package com.jdhb.game.mappers

import com.jdhb.game.controller.dtos.BetDto
import com.jdhb.game.entities.BetEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BetMapper : EntityMapper<BetDto, BetEntity>

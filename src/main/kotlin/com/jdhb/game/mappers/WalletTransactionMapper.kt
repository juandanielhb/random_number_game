package com.jdhb.game.mappers

import com.jdhb.game.controller.dtos.WalletTransactionDTO
import com.jdhb.game.entities.WalletTransactionEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface WalletTransactionMapper: EntityMapper<WalletTransactionDTO, WalletTransactionEntity> {

    @Mapping(source = "player.id", target = "playerId")
    override fun toDto(entity: WalletTransactionEntity): WalletTransactionDTO
}
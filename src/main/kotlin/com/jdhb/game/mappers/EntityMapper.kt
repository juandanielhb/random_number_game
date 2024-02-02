package com.jdhb.game.mappers

import org.mapstruct.InheritInverseConfiguration

interface EntityMapper<D, E> {
    fun toDto(entity: E): D
    @InheritInverseConfiguration
    fun toEntity(dto: D): E
    fun toDto(entityList: List<E>): List<D>
    fun toEntity(dtoList: List<D>): List<E>
}
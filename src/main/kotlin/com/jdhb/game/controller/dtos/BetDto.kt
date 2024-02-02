package com.jdhb.game.controller.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class BetDto(

    @JsonProperty(value = "player")
    val playerId: Long,
    @field:Positive
    val betAmount: Int,
    @field:Max(10)
    @field:Min(1)
    val selectedNumber: Int,
)
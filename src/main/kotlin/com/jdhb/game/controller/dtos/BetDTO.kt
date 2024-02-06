package com.jdhb.game.controller.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.jdhb.game.entities.enums.BetResults
import jakarta.persistence.Column
import jakarta.validation.constraints.*
import java.time.LocalDateTime


@JsonInclude(JsonInclude.Include.NON_NULL)
data class BetDTO(

    @field:NotNull(message = "playerId cannot be null")
    val playerId: Long,

    @field:Positive
    @field:NotNull(message = "amount cannot be null")
    val betAmount: Double,

    @field:Positive
    @field:NotNull(message = "selectedNumber cannot be null")
    val selectedNumber: Int,

    var generatedNumber: Int = 0,

    var result: BetResults = BetResults.LOSE,

    var multiplier: Double = 0.0,

    var winnings: Double = 0.0,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),
)


package com.jdhb.game.controller.dtos

import java.time.LocalDateTime

data class ErrorResponseDTO(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: MutableMap<String, String>,
    val path: String
)
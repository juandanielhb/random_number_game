package com.jdhb.game.controller.dtos

import java.time.LocalDateTime

data class ErrorResponseSingleMessageDTO(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
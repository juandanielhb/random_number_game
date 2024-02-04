package com.jdhb.game.controller

import java.time.LocalDateTime

data class ErrorResponseSingleMessage(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
package com.jdhb.game.controller

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: MutableMap<String, String>,
    val path: String
)
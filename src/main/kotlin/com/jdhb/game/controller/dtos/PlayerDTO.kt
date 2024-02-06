package com.jdhb.game.controller.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class PlayerDTO(
    val id: Long?,
    @field:NotBlank(message = "Name is required.")
    val name: String,
    @field:NotBlank(message = "Surname is required.")
    val surname: String,
    @field:NotBlank(message = "Username is required.")
    @field:Size(min = 6, max = 32, message = "Name must be between 6 and 32 characters long")
    val username: String,
)
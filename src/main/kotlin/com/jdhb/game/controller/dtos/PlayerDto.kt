package com.jdhb.game.controller.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

//@JsonInclude(JsonInclude.Include.NON_NULL)
data class PlayerDto(

    val id: Long?,
    @field:NotBlank(message = "Name is required.")
    val name: String,
    @field:NotBlank(message = "Surname is required.")
    val surname: String,
    @field:NotBlank(message = "Username is required.")
    @field:Size(min = 6, max = 32, message = "Name must be between 6 and 32 characters long")
    val username: String,
)
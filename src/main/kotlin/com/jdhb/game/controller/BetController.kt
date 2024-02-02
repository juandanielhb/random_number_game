package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.BetDto
import com.jdhb.game.services.BetService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/bets")
class BetController(val betService: BetService) {

    @PostMapping
    fun placeBet(@Valid @RequestBody betDto: BetDto): ResponseEntity<BetDto> {
        return ResponseEntity.ok(betService.save(betDto))
    }

}
package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.Player
import com.jdhb.game.services.PlayerService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/player")
class PlayerController(val playerService: PlayerService) {

    @PostMapping
    fun register(@Valid @RequestBody player: Player): ResponseEntity<Player> {
        return ResponseEntity.ok(playerService.register(player))
    }

    @GetMapping("/{id}")
    fun getPlayer(@PathVariable id:Long) = playerService.getPlayer(id)

}
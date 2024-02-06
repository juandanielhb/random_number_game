package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.PlayerDTO
import com.jdhb.game.controller.dtos.WalletTransactionDTO
import com.jdhb.game.services.BetService
import com.jdhb.game.services.PlayerService
import com.jdhb.game.services.WalletTransactionService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/players")
class PlayerController(
    private val playerService: PlayerService,
    private val betService: BetService,
    private val walletTransactionService: WalletTransactionService
) {

    @PostMapping
    fun register(@Valid @RequestBody playerDTO: PlayerDTO): ResponseEntity<PlayerDTO> {
        return ResponseEntity.ok(playerService.register(playerDTO))
    }

    @GetMapping("/{playerId}/wallet-transactions")
    fun getAllTransactionsByUser(
        @PathVariable playerId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?
    ): ResponseEntity<List<WalletTransactionDTO>> {
        val pageable = PageRequest.of(page, size)

        val transactions: List<WalletTransactionDTO>

        if (startDate != null && endDate != null) {
            transactions = walletTransactionService.getTransactionsByUserAndDateRange(playerId, startDate, endDate, pageable)
        } else {
            transactions = walletTransactionService.getAllTransactionsByUser(playerId, pageable)
        }

        return ResponseEntity.ok(transactions)
    }

    @GetMapping("/{playerId}/bets")
    fun getAllBetsByUser(
        @PathVariable playerId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<List<BetDTO>> {
        val pageable = PageRequest.of(page, size)

        val bets = betService.getBetsByPlayerId(playerId, pageable)

        return ResponseEntity.ok(bets)
    }

    @GetMapping("/{playerId}")
    fun getPlayer(@PathVariable playerId:Long) = playerService.getPlayer(playerId)
}
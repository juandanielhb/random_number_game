package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.PlayerDto
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.services.WalletTransactionService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallet-transactions")
class WalletTransactionController(private val walletTransactionService: WalletTransactionService) {

    @GetMapping("/user/{userId}")
    fun getAllTransactionsByUser(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<WalletTransactionEntity>> {
        val pageable = PageRequest.of(page, size)
        val transactions = walletTransactionService.getAllTransactionsByUser(userId, pageable)

        return ResponseEntity.ok(transactions.content)
    }

    @PostMapping
    fun register(@Valid @RequestBody walletTransactionEntity: WalletTransactionEntity): ResponseEntity<WalletTransactionEntity> {
        return ResponseEntity.ok(walletTransactionService.save(walletTransactionEntity))
    }
}
package com.jdhb.game.controller

import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.services.WalletTransactionService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/wallet-transactions")
class WalletTransactionController(private val walletTransactionService: WalletTransactionService) {

    @GetMapping("/users/{userId}")
    fun getAllTransactionsByUser(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?
    ): ResponseEntity<List<WalletTransactionEntity>> {
        val pageable = PageRequest.of(page, size)

        val transactions: Page<WalletTransactionEntity>

        if (startDate != null && endDate != null) {
            transactions = walletTransactionService.getTransactionsByUserAndDateRange(userId, startDate, endDate, pageable)
        } else {
            transactions = walletTransactionService.getAllTransactionsByUser(userId, pageable)
        }


        return ResponseEntity.ok(transactions.content)
    }

    @PostMapping
    fun registerTransaction(@Valid @RequestBody walletTransactionEntity: WalletTransactionEntity): ResponseEntity<WalletTransactionEntity> {
        return ResponseEntity.ok(walletTransactionService.save(walletTransactionEntity))
    }
}
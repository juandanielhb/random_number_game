package com.jdhb.game.controller.dtos

import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.enums.TransactionType
import jakarta.persistence.*
import java.time.LocalDateTime

class WalletTransactionDTO(
    var id: Long? = null,
    var transactionType: TransactionType = TransactionType.BET,
    var playerId: Long = 0,
    var amount: Double = 0.0,
    var walletBalance: Double = 0.0,
    val timestamp: LocalDateTime? = LocalDateTime.now(),
)
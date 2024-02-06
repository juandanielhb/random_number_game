package com.jdhb.game.entities

import com.jdhb.game.entities.enums.TransactionType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "WALLET_TRANSACTION")
data class WalletTransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType = TransactionType.BET,

    @Column(nullable = false)
    val amount: Double,

    var walletBalance: Double,

    @Column(nullable = false)
    val timestamp: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    var player: PlayerEntity,

)

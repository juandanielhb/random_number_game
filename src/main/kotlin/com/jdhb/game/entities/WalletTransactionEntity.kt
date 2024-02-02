package com.jdhb.game.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "WALLET_TRANSACTION")
data class WalletTransactionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    var player: PlayerEntity?,

    @Column(nullable = false)
    val transactionType: String,

    @Column(nullable = false)
    val amount: Int,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
)


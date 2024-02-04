package com.jdhb.game.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,


    var balance: Double = 1000.0,
)
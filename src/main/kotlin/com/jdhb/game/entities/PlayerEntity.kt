package com.jdhb.game.entities

import jakarta.persistence.*

@Entity(name = "PLAYER")
data class PlayerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    val name: String?,

    val surname: String?,

    @Column(unique = true)
    val username: String?,

    @Column(name = "wallet_balance")
    val walletBalance: Int?,
)
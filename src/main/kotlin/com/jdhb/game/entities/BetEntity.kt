package com.jdhb.game.entities

import jakarta.persistence.*

@Entity(name = "BET")
data class BetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @JoinColumn(name = "player_id")
    @ManyToOne
    var player: PlayerEntity?,

    @Column(name = "bet_amount")
    val betAmount: Int?,

    @Column(name = "select_number")
    val selectedNumber: Int?,

    val result: String?,
)

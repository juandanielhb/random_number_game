package com.jdhb.game.entities

import com.jdhb.game.entities.enums.BetResults
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "BET")
data class BetEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @JoinColumn(name = "player_id")
    @ManyToOne
    var player: PlayerEntity,

    @Column(name = "bet_amount")
    val betAmount: Double,

    @Column(name = "selected_number")
    val selectedNumber: Int = 0,

    @Enumerated(EnumType.STRING)
    val result: BetResults?,

    val multiplier: Double? = 0.0,

    var generatedNumber: Int,

    var winnings: Double = 0.0,

    @Column(nullable = false)
    val timestamp: LocalDateTime? = LocalDateTime.now(),
)

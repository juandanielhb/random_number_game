package com.jdhb.game.entities

import jakarta.persistence.*



@Entity
@Table(name = "LEADERBOARD")
data class LeaderboardEntity(
    @EmbeddedId
    val id: LeaderboardId,

    @Column(name = "total_winnings", nullable = false)
    var totalWinnings: Int
)
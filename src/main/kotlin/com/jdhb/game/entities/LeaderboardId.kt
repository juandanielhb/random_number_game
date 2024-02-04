package com.jdhb.game.entities

import com.jdhb.game.entities.enums.GameStrategies
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Embeddable
data class LeaderboardId(
    @ManyToOne
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    val player: PlayerEntity,

    @Column(name = "play_mode", nullable = false)
    val playMode: String = GameStrategies.STANDARD.value
)
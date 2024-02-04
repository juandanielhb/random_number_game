package com.jdhb.game.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "PLAYER")
data class PlayerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String?,

    val surname: String?,

    @Column(unique = true)
    val username: String?,

    @OneToOne(cascade = [CascadeType.ALL])
    val wallet: WalletEntity,

    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

)
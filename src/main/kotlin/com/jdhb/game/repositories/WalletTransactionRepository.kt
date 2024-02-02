package com.jdhb.game.repositories

import com.jdhb.game.entities.WalletTransactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface WalletTransactionRepository : JpaRepository<WalletTransactionEntity, Long> {

    fun findByPlayerId(playerId: Long, pageable: Pageable): Page<WalletTransactionEntity>
}
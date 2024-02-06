package com.jdhb.game.repositories

import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletTransactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BetRepository : JpaRepository<BetEntity, Long> {
    fun findByPlayerId(playerId: Long, pageable: Pageable): Page<BetEntity>

}
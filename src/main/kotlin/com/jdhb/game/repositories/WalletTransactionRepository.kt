package com.jdhb.game.repositories

import com.jdhb.game.entities.WalletTransactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WalletTransactionRepository : JpaRepository<WalletTransactionEntity, Long> {

    fun findByPlayerId(playerId: Long, pageable: Pageable): Page<WalletTransactionEntity>

    fun findByPlayerIdAndTimestampBetween(
        playerId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): Page<WalletTransactionEntity>
}
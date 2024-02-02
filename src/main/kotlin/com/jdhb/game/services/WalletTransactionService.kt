package com.jdhb.game.services

import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.repositories.WalletTransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WalletTransactionService(private val walletTransactionRepository: WalletTransactionRepository) {
    fun getAllTransactionsByUser(userId: Long, pageable: Pageable): Page<WalletTransactionEntity> {
        return walletTransactionRepository.findByPlayerId(userId, pageable)
    }

    fun save(walletTransactionEntity: WalletTransactionEntity): WalletTransactionEntity? {
        return walletTransactionRepository.save(walletTransactionEntity);
    }
}
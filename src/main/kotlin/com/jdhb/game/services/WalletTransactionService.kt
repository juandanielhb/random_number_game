package com.jdhb.game.services

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.controller.dtos.Player
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.repositories.WalletTransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WalletTransactionService(private val walletTransactionRepository: WalletTransactionRepository) {
    fun getAllTransactionsByUser(userId: Long, pageable: Pageable): Page<WalletTransactionEntity> {
        return walletTransactionRepository.findByPlayerId(userId, pageable)
    }

    fun getTransactionsByUserAndDateRange(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): Page<WalletTransactionEntity> {
        return walletTransactionRepository.findByPlayerIdAndTimestampBetween(userId, startDate, endDate, pageable)
    }

    fun save(walletTransactionEntity: WalletTransactionEntity): WalletTransactionEntity? {
        return walletTransactionRepository.save(walletTransactionEntity);
    }

    fun saveAll(transactions: MutableList<WalletTransactionEntity>): MutableList<WalletTransactionEntity> {
        return walletTransactionRepository.saveAll(transactions)
    }

    fun processBetTransaction(player: PlayerEntity, bet: Bet): WalletTransactionEntity {
        player.wallet.balance -= bet.betAmount
        return WalletTransactionEntity(
            transactionType = TransactionType.BET.name,
            amount = -bet.betAmount,
            player = player,
            walletBalance = player.wallet.balance
        )
    }

    fun processWinTransaction(player: PlayerEntity, bet: Bet): WalletTransactionEntity? {
        if (bet.result == BetResults.WIN) {
            player.wallet.balance += bet.winnings
            return WalletTransactionEntity(
                transactionType = TransactionType.WIN.name,
                amount = bet.winnings,
                player = player,
                walletBalance = player.wallet.balance
            )
        }
        return null
    }
}
package com.jdhb.game.services

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.controller.dtos.WalletTransactionDTO
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.mappers.WalletTransactionMapper
import com.jdhb.game.repositories.WalletTransactionRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WalletTransactionService(
    private val walletTransactionRepository: WalletTransactionRepository,
    private val walletTransactionMapper: WalletTransactionMapper,
) {
    fun getAllTransactionsByUser(playerId: Long, pageable: Pageable): List<WalletTransactionDTO> {
        return walletTransactionMapper.toDto(
            walletTransactionRepository.findByPlayerId(playerId, pageable).content
        )
    }

    fun getTransactionsByUserAndDateRange(
        playerId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): List<WalletTransactionDTO> {
        return walletTransactionMapper.toDto(
            walletTransactionRepository.findByPlayerIdAndTimestampBetween(playerId, startDate, endDate, pageable).content
        )
    }

    fun save(walletTransactionEntity: WalletTransactionEntity): WalletTransactionEntity? {
        return walletTransactionRepository.save(walletTransactionEntity);
    }

    fun saveAll(transactions: MutableList<WalletTransactionEntity>): MutableList<WalletTransactionEntity> {
        return walletTransactionRepository.saveAll(transactions)
    }

    fun processBetTransaction(player: PlayerEntity, bet: BetDTO): WalletTransactionEntity {
        player.wallet.balance -= bet.betAmount
        return WalletTransactionEntity(
            transactionType = TransactionType.BET,
            amount = -bet.betAmount,
            player = player,
            walletBalance = player.wallet.balance
        )
    }

    fun processWinTransaction(player: PlayerEntity, bet: BetDTO): WalletTransactionEntity? {
        if (bet.result == BetResults.WIN) {
            player.wallet.balance += bet.winnings
            return WalletTransactionEntity(
                transactionType = TransactionType.WIN,
                amount = bet.winnings,
                player = player,
                walletBalance = player.wallet.balance
            )
        }
        return null
    }
}
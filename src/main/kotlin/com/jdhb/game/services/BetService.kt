package com.jdhb.game.services

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.exceptions.*
import com.jdhb.game.mappers.BetMapper
import com.jdhb.game.repositories.BetRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}
@Service
class BetService(
    private val betRepository: BetRepository,
    private val betMapper: BetMapper,
    private val playerService: PlayerService,
    private val walletTransactionService: WalletTransactionService,
) {
    fun validateBet(bet: Bet, rangeStart: Int, rangeEnd: Int) {
        val player: PlayerEntity
        try {
            player = playerService.validatePlayer(bet.playerId)
            playerService.validatePlayerBalance(player, bet.betAmount)
            validateBetSelectedNumber(bet.selectedNumber, rangeStart, rangeEnd)
        } catch (ex: Exception){
            when (ex) {
                is PlayerInvalidException,
                is PlayerBalanceInvalidException,
                is BetSelectedNumberInvalidException -> throw InvalidBetException("bet: $bet is invalid. Reason: ${ex.message}")
                else -> throw ex
            }
        }
    }

    @Transactional
    fun processBets(bets: List<Bet>, transactionType: TransactionType) {
        val transactions = mutableListOf<WalletTransactionEntity>()

        bets.forEach { bet ->
            val playerId = bet.playerId
            val player = playerService.getPlayer(playerId)

            // Update player's updatedAt regardless of transaction type
            player.updatedAt = LocalDateTime.now()

            val transaction: WalletTransactionEntity? = when (transactionType) {
                TransactionType.BET -> walletTransactionService.processBetTransaction(player, bet)
                TransactionType.WIN -> walletTransactionService.processWinTransaction(player, bet)
                else -> throw IllegalArgumentException("Invalid Transaction type $transactionType")
            }

            transaction?.let {
                transactions.add(it)
            }
        }

        walletTransactionService.saveAll(transactions)
    }

    private fun validateBetSelectedNumber(selectedNumber: Int, rangeStart: Int, rangeEnd: Int) {
        if (selectedNumber !in rangeStart..rangeEnd)
            throw BetSelectedNumberInvalidException("Number should be between $rangeStart and $rangeEnd")
    }

    fun checkSingleBetPerPlayer(bets: List<Bet>){
        val playerSet = bets.map { it.playerId }.toSet()

        if (bets.size != playerSet.size) {
            throw InvalidBetException("Invalid bets. Only one bet allowed per player")
        }
    }

    fun saveBets(bets: List<Bet>): MutableList<BetEntity> {
        return betRepository.saveAll(betMapper.toEntity(bets))
    }
}
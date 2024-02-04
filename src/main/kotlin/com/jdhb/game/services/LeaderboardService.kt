package com.jdhb.game.services

import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.LeaderboardEntity
import com.jdhb.game.entities.LeaderboardId
import com.jdhb.game.entities.WalletTransactionEntity
import com.jdhb.game.entities.enums.BetResults
import com.jdhb.game.repositories.LeaderboardRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val leaderboardRepository: LeaderboardRepository
) {
    fun getLeaderboardByPlayMode(playMode: String, pageable: Pageable): Page<LeaderboardEntity> {
        return leaderboardRepository.findAllByIdPlayModeOrderByTotalWinningsDesc(playMode, pageable)
    }

    fun getLeaderboard(pageable: Pageable): Page<LeaderboardEntity> {
        return leaderboardRepository.findAll(pageable)
    }

    fun updateLeaderboardWithBets(betsResult: MutableList<BetEntity>, playMode: String) {
        betsResult.forEach { bet ->
            val winnings = 1
            val leaderboardId = LeaderboardId(bet.player, playMode)

            if (bet.result == BetResults.WIN) {
                var leaderboardEntity = leaderboardRepository.findById(leaderboardId)
                    .orElse(null)

                leaderboardEntity = leaderboardEntity?.run {
                    // If the entity exists, update its winnings
                    copy(totalWinnings = totalWinnings + winnings)
                } ?: LeaderboardEntity(leaderboardId, winnings)

                leaderboardRepository.save(leaderboardEntity)
            }
        }
    }
}
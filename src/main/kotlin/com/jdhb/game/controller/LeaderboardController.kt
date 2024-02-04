package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.LeaderboardDTO
import com.jdhb.game.controller.dtos.LeaderboardItemDTO
import com.jdhb.game.entities.LeaderboardEntity
import com.jdhb.game.repositories.LeaderboardRepository
import com.jdhb.game.services.LeaderboardService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/leaderboards")
class LeaderboardController(
    private val leaderboardService: LeaderboardService
) {

    @GetMapping
    fun getLeaderboard(
        @RequestParam(defaultValue = "all") playMode: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): LeaderboardDTO {
        val pageable = PageRequest.of(page, size)

        val leaderboardEntities = if ("all" == playMode) {
            leaderboardService.getLeaderboardByPlayMode(playMode, pageable)
        } else {
            leaderboardService.getLeaderboardByPlayMode(playMode, pageable)
        }

        var leaderboardItems = mutableListOf<LeaderboardItemDTO>()

        leaderboardEntities.content.forEachIndexed { index, leaderboardEntity ->
            leaderboardItems.add(LeaderboardItemDTO(
                rank = index + 1,
                username = leaderboardEntity.id.player.username ?: "unknown",
                totalWinnings = leaderboardEntity.totalWinnings
            ))
        }

        return LeaderboardDTO(playMode, leaderboardItems)
    }
}
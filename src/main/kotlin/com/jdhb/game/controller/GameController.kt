package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.BetDTO
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.services.BetService
import com.jdhb.game.services.GameStrategy
import com.jdhb.game.services.LeaderboardService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/games")
@Validated
class GameController (
    val gameStrategies: List<GameStrategy>,
    private val betService: BetService,
    private val leaderboardService: LeaderboardService,
) {
    @PostMapping("/{playMode}/play")
    fun playGame(@RequestBody @Valid @NotEmpty bets: List<BetDTO>,
                 @PathVariable playMode: String
    ): List<BetDTO> {
        val gameStrategy = gameStrategies.find {
            playMode == it.getStrategy()
        }
        gameStrategy ?: throw IllegalArgumentException("Invalid strategy: $playMode")

        val validatedBets = gameStrategy.validateBets(bets)
        betService.processBets(validatedBets, TransactionType.BET)

        val betsResult = gameStrategy.getResults(validatedBets)
        betService.processBets(betsResult, TransactionType.WIN)

        val betEntities = betService.saveBets(betsResult)
        leaderboardService.updateLeaderboardWithBets(betEntities, playMode)

        return betsResult
    }
}
package com.jdhb.game.controller

import com.jdhb.game.controller.dtos.Bet
import com.jdhb.game.entities.enums.TransactionType
import com.jdhb.game.services.BetService
import com.jdhb.game.services.GameStrategy
import com.jdhb.game.services.LeaderboardService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game")
@Validated
class GameController constructor(
    private val gameStrategies: List<GameStrategy>,
    private val betService: BetService,
    private val leaderboardService: LeaderboardService,
) {
    @PostMapping("/play")
    fun playGame(@RequestBody @Valid @NotEmpty bets: List<Bet>, @RequestParam(defaultValue = "standard") playMode: String): List<Bet> {
        var gameStrategy = gameStrategies.find {
            gameStrategy -> playMode == gameStrategy.getStrategy()
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
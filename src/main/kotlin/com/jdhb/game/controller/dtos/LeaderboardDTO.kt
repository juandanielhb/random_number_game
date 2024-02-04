package com.jdhb.game.controller.dtos

data class LeaderboardDTO(
    val playMode: String,
    val leaderBoardItems: List<LeaderboardItemDTO>,
)
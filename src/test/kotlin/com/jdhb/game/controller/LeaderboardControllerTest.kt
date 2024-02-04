package com.jdhb.game.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jdhb.game.services.LeaderboardService
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.jdhb.game.controller.dtos.LeaderboardDTO
import com.jdhb.game.controller.dtos.LeaderboardItemDTO
import com.jdhb.game.entities.LeaderboardEntity
import com.jdhb.game.entities.LeaderboardId
import com.jdhb.game.entities.PlayerEntity
import com.jdhb.game.entities.WalletEntity
import io.mockk.every
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LeaderboardControllerTest {

    private lateinit var leaderboardService: LeaderboardService
    private lateinit var leaderboardController: LeaderboardController
    private lateinit var mockMvc: MockMvc
    private lateinit var mockLeaderboardEntities: List<LeaderboardEntity>
    private lateinit var mockLeaderboardDTO: LeaderboardDTO

    @BeforeEach
    fun setup() {
        val playMode = "mockPlayMode"
        var player = PlayerEntity(
            id = 1,
            name = "",
            surname = "",
            username = "winner1",
            wallet = WalletEntity(),
        )
        var player2 = PlayerEntity(
            id = 2,
            name = "",
            surname = "",
            username = "winner2",
            wallet = WalletEntity(),
        )

        mockLeaderboardEntities = listOf(
            LeaderboardEntity(LeaderboardId(player, playMode), 10),
            LeaderboardEntity(LeaderboardId(player2, playMode), 10),
        )

        mockLeaderboardDTO = LeaderboardDTO(
            playMode = playMode,
            leaderBoardItems = mockLeaderboardEntities.mapIndexed { index, entity ->
                LeaderboardItemDTO(
                    rank = index + 1,
                    username = entity.id.player.username ?: "unknown",
                    totalWinnings = entity.totalWinnings
                )
            }
        )

        leaderboardService = mockk()
        leaderboardController = LeaderboardController(leaderboardService)
        mockMvc = MockMvcBuilders.standaloneSetup(leaderboardController).build()
    }

    @Test
    fun testGetLeaderboard() {
        val pageable = PageRequest.of(0, 10)
        val mockPage = PageImpl(mockLeaderboardEntities, pageable, mockLeaderboardEntities.size.toLong())

        every { leaderboardService.getLeaderboardByPlayMode("mockPlayMode", pageable) } returns mockPage

        mockMvc.perform(get("/leaderboards")
            .param("playMode", "mockPlayMode")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.playMode").value("mockPlayMode"))
            .andExpect(jsonPath("$.leaderBoardItems[0].username").value("winner1"))
    }
}
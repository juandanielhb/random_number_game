package com.jdhb.game.services

import com.jdhb.game.entities.*
import com.jdhb.game.repositories.LeaderboardRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.Pageable
import com.jdhb.game.entities.enums.BetResults
import org.junit.jupiter.api.Assertions
import org.springframework.data.domain.Page
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.mockito.Mockito.*

class LeaderboardServiceTest {

    @Mock
    private lateinit var leaderboardRepository: LeaderboardRepository

    @InjectMocks
    private lateinit var leaderboardService: LeaderboardService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetLeaderboardByPlayMode() {
        val playMode = "standard"
        val pageable: Pageable = mock()
        val expectedPage: Page<LeaderboardEntity> = mock()

        `when`(leaderboardRepository.findAllByIdPlayModeOrderByTotalWinningsDesc(playMode, pageable))
            .thenReturn(expectedPage)

        val result = leaderboardService.getLeaderboardByPlayMode(playMode, pageable)

        assertEquals(expectedPage, result)
    }

    @Test
    fun testGetLeaderboard() {
        val pageable: Pageable = mock()
        val expectedPage: Page<LeaderboardEntity> = mock()

        `when`(leaderboardRepository.findAll(pageable)).thenReturn(expectedPage)

        val result = leaderboardService.getLeaderboard(pageable)

        assertEquals(expectedPage, result)
    }

    @Test
    fun testUpdateLeaderboardWithBets() {
        val playMode = "standard"
        val playerEntity1 = PlayerEntity(id = 1, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val playerEntity2 = PlayerEntity(id = 2, username = "testUser", name = "test", surname = "test", wallet = WalletEntity())
        val betEntity1 = BetEntity(id = 1,
            player = playerEntity1,
            betAmount = 10.0,
            selectedNumber = 6,
            result = BetResults.WIN,
            multiplier = 5.0,
            generatedNumber = 5,
            )
        val betEntity2 = BetEntity(id = 1,
            player = playerEntity2,
            betAmount = 10.0,
            selectedNumber = 6,
            result = BetResults.LOSE,
            multiplier = 0.0,
            generatedNumber = 1,
        )
        val betsResult = mutableListOf(betEntity1, betEntity2)

        val leaderboardEntity1 = LeaderboardEntity(LeaderboardId(player = playerEntity1, playMode = playMode), totalWinnings = 5)
        val leaderboardEntity2 = LeaderboardEntity(LeaderboardId(player = playerEntity2, playMode = playMode), totalWinnings = 10)

        `when`(leaderboardRepository.findById(LeaderboardId(player = playerEntity1, playMode = playMode)))
            .thenReturn(java.util.Optional.of(leaderboardEntity1))

        `when`(leaderboardRepository.findById(LeaderboardId(player = playerEntity2, playMode = playMode)))
            .thenReturn(java.util.Optional.of(leaderboardEntity2))

        `when`(leaderboardRepository.save(any())).thenAnswer {
            val savedEntity: LeaderboardEntity = it.arguments[0] as LeaderboardEntity
            if (savedEntity.id.player.id?.toInt() == 1) {
                assertEquals(6, savedEntity.totalWinnings)
            } else {
                fail("Unexpected call to save with player id ${savedEntity.id.player}")
            }
            savedEntity
        }

        leaderboardService.updateLeaderboardWithBets(betsResult, playMode)

        verify(leaderboardRepository, times(1)).findById(LeaderboardId(player = playerEntity1, playMode = playMode))
        verify(leaderboardRepository, never()).findById(LeaderboardId(player = playerEntity2, playMode = playMode))

        verify(leaderboardRepository, times(1)).save(any())
    }
}
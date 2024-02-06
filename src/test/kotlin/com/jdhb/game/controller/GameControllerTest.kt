package com.jdhb.game.controller

import com.jdhb.game.services.BetService
import com.jdhb.game.services.GameStrategy
import com.jdhb.game.services.LeaderboardService
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameControllerTest {

    @Mock
    private lateinit var gameStrategiesMock: List<GameStrategy>

    @Mock
    private lateinit var gameStrategyMock: GameStrategy

    @Mock
    private lateinit var betServiceMock: BetService

    @Mock
    private lateinit var leaderboardServiceMock: LeaderboardService

    @InjectMocks
    private lateinit var gameController: GameController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

}
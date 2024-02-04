package com.jdhb.game.repositories

import com.jdhb.game.entities.LeaderboardEntity
import com.jdhb.game.entities.LeaderboardId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LeaderboardRepository : JpaRepository<LeaderboardEntity, LeaderboardId> {
    fun findAllByIdPlayModeOrderByTotalWinningsDesc(playMode: String, pageable: Pageable): Page<LeaderboardEntity>

}
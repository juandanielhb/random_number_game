package com.jdhb.game.repositories

import com.jdhb.game.entities.BetEntity
import com.jdhb.game.entities.PlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BetRepository : JpaRepository<BetEntity, Long> {}
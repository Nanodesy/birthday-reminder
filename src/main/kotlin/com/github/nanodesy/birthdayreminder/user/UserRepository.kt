package com.github.nanodesy.birthdayreminder.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
  fun findByTelegramId(telegramId: Long): Optional<UserEntity>
  fun existsByTelegramId(telegramId: Long): Boolean
}
package com.github.nanodesy.birthdayreminder.person

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<PersonEntity, Long> {
  fun findAllByUserTelegramId(userTelegramId: Long, pageable: Pageable): Page<PersonEntity>
}
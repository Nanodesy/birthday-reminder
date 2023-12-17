package com.github.nanodesy.birthdayreminder.reminder

import com.github.nanodesy.birthdayreminder.person.PersonEntity
import com.github.nanodesy.birthdayreminder.person.mapPersonEntityToPerson
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface ReminderRepository {
  fun findAllBirthdaysByDate(date: LocalDate): List<ReminderTask>
}

@Repository
class ReminderRepositoryImpl : ReminderRepository {
  @PersistenceContext
  lateinit var entityManager: EntityManager

  override fun findAllBirthdaysByDate(date: LocalDate): List<ReminderTask> {
    val personEntities: List<PersonEntity> = entityManager.createNativeQuery(
      """
        SELECT telegram_id, person.*
        FROM person
            JOIN "user" USING (user_id)
        WHERE extract(DAY FROM birthdate) = :day
            AND extract(MONTH FROM birthdate) = :month
    """.trimIndent(), PersonEntity::class.java
    )
      .setParameter("day", date.dayOfMonth)
      .setParameter("month", date.monthValue)
      .resultList as List<PersonEntity>

    return personEntities
      .map { ReminderTask(it.user!!.telegramId, mapPersonEntityToPerson(it)) }
  }

}
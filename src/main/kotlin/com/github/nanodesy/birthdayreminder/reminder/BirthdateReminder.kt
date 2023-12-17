package com.github.nanodesy.birthdayreminder.reminder

import com.github.nanodesy.birthdayreminder.reminder.sender.ReminderSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class BirthdateReminder(
  private val reminderRepository: ReminderRepository,
  private val reminderSender: ReminderSender
) {

  @Scheduled(cron = "@hourly")
  fun remind() {
    val currentDate = LocalDate.now()
    val reminderTasks = reminderRepository.findAllBirthdaysByDate(currentDate)
    reminderTasks.forEach { reminderSender.send(it) }
  }
}
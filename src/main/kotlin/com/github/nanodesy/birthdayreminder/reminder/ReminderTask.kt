package com.github.nanodesy.birthdayreminder.reminder

import com.github.nanodesy.birthdayreminder.person.Person

data class ReminderTask(
  val telegramId: Long,
  val person: Person
)
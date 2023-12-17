package com.github.nanodesy.birthdayreminder.reminder.sender

import com.github.nanodesy.birthdayreminder.bot.Bot
import com.github.nanodesy.birthdayreminder.reminder.ReminderTask
import org.springframework.stereotype.Component

interface ReminderSender {
  fun send(reminderTask: ReminderTask)
}

@Component
class ReminderSenderImpl(
  private val bot: Bot,
) : ReminderSender {
  override fun send(reminderTask: ReminderTask) {
    val person = reminderTask.person
    bot.sendMessageToUser(
      reminderTask.telegramId,
      "Today is a birthday for ${person.lastname} ${person.firstname} ${person.middlename}."
    )
  }
}
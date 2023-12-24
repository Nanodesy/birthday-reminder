package com.github.nanodesy.birthdayreminder.reminder.sender

import com.github.nanodesy.birthdayreminder.bot.Bot
import com.github.nanodesy.birthdayreminder.person.Person
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
      getReminderMessage(person)
    )
  }

  private fun getReminderMessage(person: Person): String {
    val sb = StringBuilder()
    sb.append("Today is a birthday for ${person.lastname} ${person.firstname}")
    if (person.middlename != null)
      sb.append(" ${person.middlename}")
    sb.append(".")
    return sb.toString()
  }
}
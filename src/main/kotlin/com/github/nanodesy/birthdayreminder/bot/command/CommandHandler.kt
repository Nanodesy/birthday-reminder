package com.github.nanodesy.birthdayreminder.bot.command

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

interface CommandHandler {
  fun getCommand(): String

  fun getDescription(): String

  fun handle(update: Update): SendMessage?
}
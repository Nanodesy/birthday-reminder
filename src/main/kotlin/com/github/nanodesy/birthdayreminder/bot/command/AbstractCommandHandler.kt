package com.github.nanodesy.birthdayreminder.bot.command

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

abstract class AbstractCommandHandler : CommandHandler {
  override fun handle(update: Update): SendMessage? {
    val userTelegramId = update.message.from.id
    val messageText = update.message.text
    val sendMessage = SendMessage()
    sendMessage.text = handleMessage(userTelegramId, messageText)
    sendMessage.setChatId(userTelegramId)
    return sendMessage
  }

  protected open fun handleMessage(userTelegramId: Long, messageText: String): String {
    throw UnsupportedOperationException()
  }
}
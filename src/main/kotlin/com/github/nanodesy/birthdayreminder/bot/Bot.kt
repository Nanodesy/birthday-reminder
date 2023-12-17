package com.github.nanodesy.birthdayreminder.bot

import com.github.nanodesy.birthdayreminder.bot.command.CommandHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class Bot(
  @Value("\${bot.username}")
  private val username: String,
  @Value("\${bot.token}")
  private val token: String,
  private val commandHandlers: List<CommandHandler>
) : TelegramLongPollingBot(token) {

  override fun getBotUsername(): String {
    return username
  }

  override fun getBotToken(): String {
    return token
  }

  override fun onUpdateReceived(update: Update?) {
    val command = update?.message?.text

    if (command != null) {
      val botApiMethod = commandHandlers
        .find { command.startsWith(it.getCommand()) }
        ?.handle(update)
      botApiMethod?.let { execute(it) }
    }
  }

  fun sendMessageToUser(userTelegramId: Long, message: String) {
    val sendMessage = SendMessage()
    sendMessage.setChatId(userTelegramId)
    sendMessage.text = message
    execute(sendMessage)
  }
}
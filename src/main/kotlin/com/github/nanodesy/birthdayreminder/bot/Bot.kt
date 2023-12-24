package com.github.nanodesy.birthdayreminder.bot

import com.github.nanodesy.birthdayreminder.bot.command.CommandHandler
import com.github.nanodesy.birthdayreminder.bot.command.HelpCommandHandler
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

  companion object {
    val log: Logger = LoggerFactory.getLogger(Bot::class.java)
  }

  override fun getBotUsername(): String {
    return username
  }

  @PostConstruct
  fun printCommands() = commandHandlers
    .find { it is HelpCommandHandler }
    ?.let { it as HelpCommandHandler }
    ?.getHelpMessage()
    ?.let { log.info(it) }

  override fun onUpdateReceived(update: Update?) {
    val messageText = update?.message?.text

    if (messageText != null) {
      commandHandlers
        .find { messageText.startsWith("/${it.getCommand()}") }
        ?.handle(update)
        ?.let { execute(it) }
    }
  }

  fun sendMessageToUser(userTelegramId: Long, message: String) {
    val sendMessage = SendMessage()
    sendMessage.setChatId(userTelegramId)
    sendMessage.text = message
    execute(sendMessage)
  }
}
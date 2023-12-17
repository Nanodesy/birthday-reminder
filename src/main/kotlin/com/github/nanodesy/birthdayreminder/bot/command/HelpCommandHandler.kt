package com.github.nanodesy.birthdayreminder.bot.command

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class HelpCommandHandler : CommandHandler {

  @set: Autowired
  lateinit var commandHandlers: List<CommandHandler>

  @PostConstruct
  fun init() {
    commandHandlers = commandHandlers.toMutableList().apply { add(this@HelpCommandHandler) }
  }

  override fun getCommand(): String {
    return "/help"
  }

  override fun getDescription(): String {
    return "List of commands"
  }

  override fun handle(update: Update): SendMessage {
    val userTelegramId = update.message.from.id
    val sendMessage = SendMessage()
    sendMessage.setChatId(userTelegramId)
    sendMessage.text = getHelpMessage()
    return sendMessage
  }

  private fun getHelpMessage(): String {
    val sb = StringBuilder()
    sb.append("Bot commands:\n")
    commandHandlers.forEach {
      sb.append("${it.getCommand()} - ${it.getDescription()}\n")
    }
    return sb.toString()
  }
}
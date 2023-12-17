package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.user.User
import com.github.nanodesy.birthdayreminder.user.UserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class StartCommandHandler(
  private val userService: UserService
) : CommandHandler {
  override fun getCommand(): String {
    return "/start"
  }

  override fun getDescription(): String {
    return "Register as a user of a bot."
  }

  override fun handle(update: Update): SendMessage? {
    val userTelegramId = update.message.from.id
    val sendMessage = SendMessage()
    sendMessage.setChatId(update.message.from.id)

    if (userService.existsByTelegramId(userTelegramId)) {
      sendMessage.text = "Hello! You already registered!"
    } else {
      userService.create(User(userTelegramId))
      sendMessage.text = "Hello newcomer!"
    }

    return sendMessage
  }
}
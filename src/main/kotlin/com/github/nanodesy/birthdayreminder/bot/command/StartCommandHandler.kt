package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.user.User
import com.github.nanodesy.birthdayreminder.user.UserService
import org.springframework.stereotype.Component

@Component
class StartCommandHandler(
  private val userService: UserService
) : AbstractCommandHandler() {
  override fun getCommand(): String {
    return "start"
  }

  override fun getDescription(): String {
    return "Register as a user of a bot."
  }

  override fun handleMessage(userTelegramId: Long, messageText: String): String {
    return if (userService.existsByTelegramId(userTelegramId)) {
      "Hello! You already registered!"
    } else {
      userService.create(User(userTelegramId))
      "Hello newcomer!"
    }
  }
}
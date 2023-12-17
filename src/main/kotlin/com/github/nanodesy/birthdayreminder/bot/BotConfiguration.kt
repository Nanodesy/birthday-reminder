package com.github.nanodesy.birthdayreminder.bot

import com.github.nanodesy.birthdayreminder.bot.command.CommandHandler
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfiguration(
  private val commandHandlers: List<CommandHandler>
) {

  companion object {
    val log = LoggerFactory.getLogger(BotConfiguration::class.java)
  }

  @PostConstruct
  fun logCommands() {
    val sb = StringBuilder()
    sb.append("Bot commands:\n")
    commandHandlers.forEach {
      sb.append(
        "${
          it.getCommand().substring(1)
        } - ${it.getDescription()}\n"
      )
    }
    log.info(sb.toString())
  }

  @Bean
  fun telegramBotsApi(longPollingBot: LongPollingBot): TelegramBotsApi {
    return TelegramBotsApi(DefaultBotSession::class.java).apply {
      registerBot(longPollingBot)
    }
  }
}
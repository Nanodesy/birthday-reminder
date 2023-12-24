package com.github.nanodesy.birthdayreminder.bot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfiguration {
  @Bean
  fun telegramBotsApi(longPollingBot: LongPollingBot): TelegramBotsApi {
    return TelegramBotsApi(DefaultBotSession::class.java).apply {
      registerBot(longPollingBot)
    }
  }
}
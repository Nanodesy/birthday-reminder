package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.person.PersonService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class ListCommandHandler(
  private val personService: PersonService
) : CommandHandler {
  override fun getCommand(): String {
    return "/list"
  }

  override fun getDescription(): String {
    return "Show added birthdays. Specify page if needed."
  }

  override fun handle(update: Update): SendMessage? {
    val userTelegramId = update.message.from.id
    val messageText = update.message.text.substringAfter(getCommand()).trim()
    val sendMessage = SendMessage()
    sendMessage.setChatId(userTelegramId)

    var pageNumber = messageText.toIntOrNull() ?: 1
    pageNumber = if (pageNumber <= 0) 1 else pageNumber
    val pageable = PageRequest.of(
      pageNumber - 1,
      10,
      Sort.by(Sort.Order.asc("id"))
    )

    val birthdays = personService.getAllByUserTelegramId(userTelegramId, pageable)
    val sb = StringBuilder()
    sb.append("List of birthdays:\n")
    birthdays.forEach { sb.append("${it.id}. ${it.lastname} ${it.firstname} ${it.middlename}. ${it.birthdate}. \n") }
    sb.append("Total elements ${birthdays.totalElements}. Page $pageNumber of ${birthdays.totalPages}.")
    sendMessage.text = sb.toString()
    return sendMessage;
  }
}
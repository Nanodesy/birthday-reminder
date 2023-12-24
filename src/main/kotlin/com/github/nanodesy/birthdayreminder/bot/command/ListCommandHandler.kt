package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.person.PersonService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class ListCommandHandler(
  private val personService: PersonService
) : AbstractCommandHandler() {
  override fun getCommand(): String {
    return "list"
  }

  override fun getDescription(): String {
    return "Show added birthdays. Specify page if needed."
  }


  override fun handleMessage(userTelegramId: Long, messageText: String): String {
    val pageable = getPageable(messageText)
    val birthdays = personService.getAllByUserTelegramId(userTelegramId, pageable)
    val sb = StringBuilder()
    sb.append("List of birthdays:\n")
    birthdays.forEach { sb.append("${it.id}. ${it.lastname} ${it.firstname} ${it.middlename}. ${it.birthdate}. \n") }
    sb.append("Total elements ${birthdays.totalElements}. Page ${birthdays.number + 1} of ${birthdays.totalPages}.")
    return sb.toString()
  }

  private fun getPageable(messageText: String): Pageable {
    var pageNumber = messageText
      .substringAfter(getCommand())
      .trim()
      .toIntOrNull() ?: 1
    pageNumber = if (pageNumber <= 0) 1 else pageNumber
    return PageRequest.of(
      pageNumber - 1,
      10,
      Sort.by(Sort.Order.asc("id"))
    )
  }
}
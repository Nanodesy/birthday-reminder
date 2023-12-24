package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.person.Person
import com.github.nanodesy.birthdayreminder.person.PersonService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.time.Month
import java.time.format.TextStyle
import java.util.*

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
    birthdays.forEach { appendPerson(sb, it) }
    sb.append("Total elements ${birthdays.totalElements}. Page ${birthdays.number + 1} of ${birthdays.totalPages}.")
    return sb.toString()
  }

  private fun appendPerson(sb: StringBuilder, it: Person) {
    sb.append("- ${it.lastname}")
    sb.append(" ${it.firstname}")
    if (it.middlename != null)
      sb.append(" ${it.middlename}")
    sb.append(". ${it.birthDay}")
    sb.append(" ${Month.of(it.birthMonth).getDisplayName(TextStyle.FULL, Locale.ENGLISH)}")
    if (it.birthYear != null)
      sb.append(" ${it.birthYear}")
    sb.append(".").appendLine()
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
      Sort.by(
        Sort.Order.asc("birthMonth"),
        Sort.Order.asc("birthDay"),
        Sort.Order.asc("birthYear"),
      )
    )
  }
}
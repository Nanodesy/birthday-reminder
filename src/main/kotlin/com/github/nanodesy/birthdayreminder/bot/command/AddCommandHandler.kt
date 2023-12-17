package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.person.Person
import com.github.nanodesy.birthdayreminder.person.PersonService
import com.github.nanodesy.birthdayreminder.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@Component
class AddCommandHandler(
  private val personService: PersonService,
  private val userService: UserService
) : CommandHandler {

  companion object {
    val log = LoggerFactory.getLogger(AddCommandHandler::class.java)
  }

  override fun getCommand(): String {
    return "/add"
  }

  override fun getDescription(): String {
    return "Specify new birthday in following format: lastname firstname middlename birthday. " +
        "Birthday in format: dd.mm.yyyy"
  }

  override fun handle(update: Update): SendMessage? {
    val userTelegramId = update.message.from.id
    val messageText = update.message.text.substringAfter(getCommand()).trim()
    val sendMessage = SendMessage()
    sendMessage.setChatId(userTelegramId)

    try {
      val person: Person = convertMessageTextToPerson(messageText)
      person.userId = userService.getByTelegramId(userTelegramId).id
      personService.create(person)
      sendMessage.text = "Birthday successfully added."
    } catch (e: Exception) {
      log.error("Error while adding birthday.", e)
      sendMessage.text = "Error. Can't read answer."
    }

    return sendMessage
  }

  fun convertMessageTextToPerson(messageText: String): Person {
    val fields = messageText
      .split(Pattern.compile(" +"))
      .map { it.trim() }

    return Person(
      fields[1],
      fields[2],
      fields[0],
      LocalDate.parse(fields[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    )
  }
}
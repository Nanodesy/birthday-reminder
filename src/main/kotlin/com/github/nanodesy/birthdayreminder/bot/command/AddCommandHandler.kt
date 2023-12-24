package com.github.nanodesy.birthdayreminder.bot.command

import com.github.nanodesy.birthdayreminder.person.Person
import com.github.nanodesy.birthdayreminder.person.PersonService
import com.github.nanodesy.birthdayreminder.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.regex.Pattern
import java.util.stream.Collectors

@Component
class AddCommandHandler(
  private val personService: PersonService,
  private val userService: UserService
) : AbstractCommandHandler() {

  companion object {
    val log: Logger = LoggerFactory.getLogger(AddCommandHandler::class.java)
  }

  override fun getCommand(): String {
    return "add"
  }

  override fun getDescription(): String {
    return "Specify new birthday in following format (middlename and birth year are optional): " +
        "Lastname: Ivanov | " +
        "Firstname: Ivan | " +
        "Middlename: Jovanovich | " +
        "Birth day: 10 | " +
        "Birth month: 12 | " +
        "Birth year: 1997"
  }

  override fun handleMessage(userTelegramId: Long, messageText: String): String {
    return try {
      val person: Person = convertMessageTextToPerson(messageText)
      person.userId = userService.getByTelegramId(userTelegramId).id
      personService.create(person)
      "Birthday successfully added."
    } catch (e: Exception) {
      log.error("Error while adding birthday.", e)
      "Error. Can't read answer."
    }
  }

  fun convertMessageTextToPerson(messageText: String): Person {
    val nameValueMap = messageText.substringAfter("/add")
      .trim()
      .split(Pattern.compile("\\|"))
      .stream()
      .map { it.trim().split(":") }
      .collect(
        Collectors.toMap(
          { it: List<String> -> it[0].trim() },
          { it: List<String> -> it[1].trim() })
      )

    return Person(
      nameValueMap["Firstname"]!!,
      nameValueMap["Middlename"],
      nameValueMap["Lastname"]!!,
      nameValueMap["Birth day"]!!.toInt(),
      nameValueMap["Birth month"]!!.toInt(),
      nameValueMap["Birth year"]?.toIntOrNull()
    )
  }
}
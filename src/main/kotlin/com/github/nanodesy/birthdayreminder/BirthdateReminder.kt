package com.github.nanodesy.birthdayreminder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BirthdateReminderApplication

fun main(args: Array<String>) {
  runApplication<BirthdateReminderApplication>(*args)
}
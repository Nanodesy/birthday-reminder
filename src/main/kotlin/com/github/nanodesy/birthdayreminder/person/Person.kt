package com.github.nanodesy.birthdayreminder.person

import java.time.LocalDate

class Person(
  var id: Long?,
  var userId: Long?,
  var firstname: String,
  var middlename: String?,
  var lastname: String,
  var birthdate: LocalDate,
) {

  constructor(
    firstname: String,
    middlename: String?,
    lastname: String,
    birthdate: LocalDate
  ) : this(null, null, firstname, middlename, lastname, birthdate)
}

fun mapPersonToPersonEntity(person: Person): PersonEntity = PersonEntity(
  person.id,
  person.firstname,
  person.middlename,
  person.lastname,
  person.birthdate,
)
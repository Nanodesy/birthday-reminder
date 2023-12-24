package com.github.nanodesy.birthdayreminder.person

class Person(
  var id: Long?,
  var userId: Long?,
  var firstname: String,
  var middlename: String?,
  var lastname: String,
  var birthDay: Int,
  var birthMonth: Int,
  var birthYear: Int?
) {

  constructor(
    firstname: String,
    middlename: String?,
    lastname: String,
    birthDay: Int,
    birthMonth: Int,
    birthYear: Int?
  ) : this(null, null, firstname, middlename, lastname, birthDay, birthMonth, birthYear)
}

fun mapPersonToPersonEntity(person: Person): PersonEntity = PersonEntity(
  person.id,
  person.firstname,
  person.middlename,
  person.lastname,
  person.birthDay,
  person.birthMonth,
  person.birthYear
)
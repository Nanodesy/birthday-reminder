package com.github.nanodesy.birthdayreminder.person

import com.github.nanodesy.birthdayreminder.NotFoundException
import com.github.nanodesy.birthdayreminder.user.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService(
  private val personRepository: PersonRepository,
  private val userRepository: UserRepository
) {

  fun getById(id: Long): Person = personRepository.findById(id)
    .map { mapPersonEntityToPerson(it) }
    .orElseThrow { NotFoundException("Person with id $id not found") }

  fun getAll(pageable: Pageable): Page<Person> = personRepository.findAll(pageable)
    .map { mapPersonEntityToPerson(it) }

  fun getAllByUserTelegramId(
    userTelegramId: Long, pageable: Pageable
  ): Page<Person> = personRepository.findAllByUserTelegramId(userTelegramId, pageable)
    .map { mapPersonEntityToPerson(it) }

  fun create(person: Person): Person {
    var personEntity: PersonEntity = mapPersonToPersonEntity(person);
    personEntity.user = Optional.ofNullable(person.userId)
      .flatMap { userRepository.findById(it) }
      .orElseThrow { NotFoundException("User with id ${person.userId} not found") }
    personEntity = personRepository.save(personEntity)
    return mapPersonEntityToPerson(personEntity)
  }

  fun updateById(id: Long, person: Person): Person {
    var personEntity: PersonEntity = personRepository.findById(id)
      .orElseThrow { NotFoundException("Person with id $id not found") }
    personEntity.firstname = person.firstname
    personEntity.middlename = person.middlename
    personEntity.lastname = person.lastname
    personEntity.birthdate = person.birthdate
    personEntity = personRepository.save(personEntity)
    return mapPersonEntityToPerson(personEntity)
  }

  fun deleteById(id: Long) {
    if (!personRepository.existsById(id))
      throw NotFoundException("Person with id $id not found")
    personRepository.deleteById(id)
  }
}
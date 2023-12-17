package com.github.nanodesy.birthdayreminder.person

import com.github.nanodesy.birthdayreminder.user.UserEntity
import jakarta.persistence.*
import java.time.LocalDate

@Table(name = "person")
@Entity
class PersonEntity(
  @Id
  @Column(name = "person_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(nullable = false)
  var firstname: String,
  var middlename: String?,
  @Column(nullable = false)
  var lastname: String,
  @Column(nullable = false)
  var birthdate: LocalDate,
  @ManyToOne
  @JoinColumn(name = "user_id")
  var user: UserEntity? = null
) {
}

fun mapPersonEntityToPerson(personEntity: PersonEntity): Person = Person(
  personEntity.id,
  null,
  personEntity.firstname,
  personEntity.middlename,
  personEntity.lastname,
  personEntity.birthdate
)
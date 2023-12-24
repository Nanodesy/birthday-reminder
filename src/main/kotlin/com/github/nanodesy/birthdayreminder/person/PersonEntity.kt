package com.github.nanodesy.birthdayreminder.person

import com.github.nanodesy.birthdayreminder.user.UserEntity
import jakarta.persistence.*

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
  @Column(name = "birth_day", nullable = false)
  var birthDay: Int,
  @Column(name = "birth_month", nullable = false)
  var birthMonth: Int,
  @Column(name = "birth_year")
  var birthYear: Int?,
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
  personEntity.birthDay,
  personEntity.birthMonth,
  personEntity.birthYear
)

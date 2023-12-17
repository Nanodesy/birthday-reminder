package com.github.nanodesy.birthdayreminder.user

import com.github.nanodesy.birthdayreminder.person.PersonEntity
import jakarta.persistence.*

@Table(name = "\"user\"")
@Entity
class UserEntity(
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,
  @Column(name = "telegram_id", nullable = false, updatable = false, unique = true)
  val telegramId: Long,
  @OneToMany(mappedBy = "user")
  var persons: Set<PersonEntity>? = null,
) {
}

fun mapUserEntityToUser(userEntity: UserEntity): User = User(
  userEntity.id,
  userEntity.telegramId
)
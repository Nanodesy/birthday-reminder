package com.github.nanodesy.birthdayreminder.user

class User(
  var id: Long?,
  val telegramId: Long,
) {
  constructor(telegramId: Long) : this(null, telegramId)
}

fun mapUserToUserEntity(user: User): UserEntity = UserEntity(
  user.id,
  user.telegramId
)
package com.github.nanodesy.birthdayreminder.user

import com.github.nanodesy.birthdayreminder.NotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
  private val userRepository: UserRepository
) {

  fun existsByTelegramId(telegramId: Long): Boolean = userRepository.existsByTelegramId(telegramId)

  fun getByTelegramId(telegramId: Long): User = userRepository.findByTelegramId(telegramId)
    .map { mapUserEntityToUser(it) }
    .orElseThrow { NotFoundException("User with telegram id $telegramId not found") }

  fun create(user: User): User {
    var userEntity: UserEntity = mapUserToUserEntity(user)
    userEntity = userRepository.save(userEntity)
    return mapUserEntityToUser(userEntity)
  }

}
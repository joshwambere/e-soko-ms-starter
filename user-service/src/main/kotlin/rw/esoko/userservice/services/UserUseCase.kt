package rw.esoko.userservice.services

import org.springframework.stereotype.Service
import rw.esoko.userservice.contracts.UserDto
import java.util.*

@Service
class UserUseCase {
    fun save(userDto:  UserDto): UserDto {
        userDto.id = UUID.randomUUID().toString()
        return userDto
    }

}
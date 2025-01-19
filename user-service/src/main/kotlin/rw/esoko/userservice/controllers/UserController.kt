package rw.esoko.userservice.controllers

import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import rw.esoko.userservice.contracts.UserDto
import rw.esoko.userservice.services.UserUseCase


@RestController
@RequestMapping(value = ["/users"])
@AllArgsConstructor
class UserController (private val userService: UserUseCase){

    @PostMapping
    fun save(@RequestBody userDto: UserDto?): ResponseEntity<UserDto> {
        if (userDto == null) {
            return ResponseEntity.badRequest().build()
        }
        val savedUser = userService?.save(userDto)
        return ResponseEntity.ok(savedUser)
    }

    @GetMapping("/secured")
    fun securedEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, from secured endpoint!")
    }
}
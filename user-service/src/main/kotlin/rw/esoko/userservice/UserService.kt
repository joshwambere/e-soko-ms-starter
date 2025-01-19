package rw.esoko.userservice


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserService

fun main(args: Array<String>) {
	runApplication<UserService>(*args)
}
package rw.esoko.iamservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IamService

fun main(args: Array<String>) {
    runApplication<IamService>(*args)
}

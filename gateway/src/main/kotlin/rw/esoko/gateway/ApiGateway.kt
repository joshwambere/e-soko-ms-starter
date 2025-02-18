package rw.esoko.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@SpringBootApplication
@EnableDiscoveryClient
class ApiGateway

fun main(args: Array<String>) {
	runApplication<ApiGateway>(*args)
}
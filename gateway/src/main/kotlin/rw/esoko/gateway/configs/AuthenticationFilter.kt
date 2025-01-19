package rw.esoko.gateway.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain

import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component


@RefreshScope
@Component
class AuthenticationFilter : GatewayFilter {
    @Autowired
    private val validator: RouterValidator? = null


    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request

        if (validator!!.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED)
            }

            val token: String = request.getHeaders().getOrEmpty("Authorization").get(0)
            if (!token.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED)
            }
        }
        println("Request path: $exchange, Authorization: ")
        return chain.filter(exchange)
    }

    private fun onError(exchange: ServerWebExchange, httpStatus: HttpStatus): Mono<Void> {
        val response: ServerHttpResponse = exchange.response
        response.setStatusCode(httpStatus)
        return response.setComplete()
    }

    private fun authMissing(request: ServerHttpRequest): Boolean {
        return !request.getHeaders().containsKey("Authorization")
    }
}
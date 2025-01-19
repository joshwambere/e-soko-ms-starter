package rw.esoko.gateway.configs


import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class RouterValidator {
    var isSecured: Predicate<ServerHttpRequest> = Predicate<ServerHttpRequest> { request ->
        openEndpoints.stream()
            .noneMatch { uri: String? -> request.getURI().getPath().contains(uri!!) }
    }

    companion object {
        val openEndpoints: List<String> = listOf(
            "/auth/register",
            "/auth/login",
            "/users",
            "/users/verify"
        )
    }
}
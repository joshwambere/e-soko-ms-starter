package rw.esoko.gateway.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableHystrix
class GatewayConfig {
    @Autowired
    private val filter: AuthenticationFilter? = null

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("user-service") { r: PredicateSpec ->
                r.path("/users/**")
                    .filters { f: GatewayFilterSpec -> f.filter(filter) }
                    .uri("lb://user-service")
            }
            .route("iam-service") { r: PredicateSpec ->
                r.path("/iam/**")
                    .filters { f: GatewayFilterSpec -> f.filter(filter) }
                    .uri("lb://iam-service")
            }
            .build()
    }
}

package com.example.grpc.web.config

import com.example.grpc.web.handler.ExampleHandler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

//@Configuration
class ExampleRoutingConfiguration(
    private val exampleHandler: ExampleHandler
) {
    @Bean
    fun copyTransaction(): RouterFunction<ServerResponse> {
        return router {
            BASE_URI.nest {
                GET(EXAMPLE_URI).invoke { exampleHandler.getExample(it)  }
            }
        }
    }

    companion object {
        const val BASE_URI = "/api"
        const val EXAMPLE_URI = "/example"
    }
}

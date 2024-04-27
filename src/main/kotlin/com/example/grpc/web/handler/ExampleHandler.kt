package com.example.grpc.web.handler

import com.example.grpc.service.ExampleService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


//@Component
class ExampleHandler(
    private val exampleService: ExampleService
) {
    fun getExample(serverRequest: ServerRequest): Mono<ServerResponse> {
        return exampleService.getExample(serverRequest.queryParam("id").orElse("1"))
            .flatMap {
                ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(it))
            }
    }
}
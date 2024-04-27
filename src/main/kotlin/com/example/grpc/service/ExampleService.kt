package com.example.grpc.service

import com.example.grpc.proto.Example.ExampleMessage
import com.example.grpc.repository.ExampleRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Mono

@Service
class ExampleService(
    private val exampleRepository: ExampleRepository,
) {

    fun getExample(@PathVariable id: String) : Mono<ExampleMessage> {
        val exampleMessage = ExampleMessage.newBuilder().setId(id).setName("name").build()
        return Mono.just(exampleMessage)
    }

}
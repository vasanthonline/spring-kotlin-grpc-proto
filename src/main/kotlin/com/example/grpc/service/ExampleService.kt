package com.example.grpc.service

import com.example.grpc.models.Example
import com.example.grpc.proto.Example.ExampleMessage
import com.example.grpc.repository.ExampleRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Mono

@Service
class ExampleService(
    private val exampleRepository: ExampleRepository,
) {

    fun getExample(id: String) : Mono<ExampleMessage> {
        return exampleRepository.findById(id).flatMap {
            Mono.just(ExampleMessage.newBuilder().setId(it.id).setName(it.name).build())
        }
    }

    fun createExample(id: String, name: String): Mono<ExampleMessage> {
        return exampleRepository.save(Example(id, name))
            .flatMap {
                Mono.just(ExampleMessage.newBuilder().setId(it.id).setName(it.name).build())
            }
    }
}
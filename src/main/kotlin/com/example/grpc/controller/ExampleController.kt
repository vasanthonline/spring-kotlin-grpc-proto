package com.example.grpc.controller

import com.example.grpc.proto.Example.ExampleMessage
import com.example.grpc.repository.ExampleRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ExampleController(
    private val exampleRepository: ExampleRepository,
) {

    @RequestMapping(value = ["/example/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getExample(@PathVariable id: String) : ExampleMessage {
        return ExampleMessage.newBuilder().setId(id).setName("name").build()
    }

}
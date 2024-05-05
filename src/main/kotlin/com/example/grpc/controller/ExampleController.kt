package com.example.grpc.controller

import com.example.grpc.proto.Example.ExampleMessage
import com.example.grpc.repository.ExampleRepository
import com.example.grpc.service.ExampleService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class ExampleController(
    private val exampleService: ExampleService,
) {

    @RequestMapping(value = ["/example/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getExample(@PathVariable id: String) : Mono<ExampleMessage> {
        return exampleService.getExample(id)
    }

    @RequestMapping(value = ["/example"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createExample(@RequestParam("id") id: String, @RequestParam("name") name: String) : Mono<ExampleMessage> {
        return exampleService.createExample(id, name)
    }

}
package com.example.grpc.repository

import com.example.grpc.proto.Example.ExampleMessage
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExampleRepository : ReactiveCrudRepository<ExampleMessage, String>
package com.example.grpc.repository

import com.example.grpc.models.Example

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExampleRepository : ReactiveCrudRepository<Example, String>
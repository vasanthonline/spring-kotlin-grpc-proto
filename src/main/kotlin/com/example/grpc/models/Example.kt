package com.example.grpc.models

import org.intellij.lang.annotations.Identifier
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("examples")
data class Example(

    @Id
    val id: String,

    val name: String
)

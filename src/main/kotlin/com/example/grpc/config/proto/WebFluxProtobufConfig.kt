package com.example.grpc.config.proto

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.protobuf.Message
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import com.google.protobuf.util.JsonFormat;
import org.springframework.http.codec.json.Jackson2JsonEncoder

@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val objectMapper = JsonMapper.builder()
            .addModule(KotlinModule.Builder().build())
            .build()
            .registerModule(SimpleModule().addSerializer(Message::class.java, object : JsonSerializer<Message>() {
                override fun serialize(value: Message, gen: JsonGenerator, serializers: SerializerProvider) {
                    val str = JsonFormat.printer().omittingInsignificantWhitespace().print(value);
                    gen.writeRawValue(str)
                }
            }))
        configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
    }
}
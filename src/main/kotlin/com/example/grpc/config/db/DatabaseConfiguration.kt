package com.example.grpc.config.db

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.time.Duration

@Configuration
@EnableR2dbcRepositories
class DatabaseConfiguration(
    @Value("\${transactions.db.host}") val host: String,
    @Value("\${transactions.db.name}") val dbName: String,
    @Value("\${transactions.db.username}") val userName: String,
    @Value("\${transactions.db.password}") val password: String,
    @Value("\${spring.r2dbc.pool.initial-size}") val initialSize: Int,
    @Value("\${spring.r2dbc.pool.max-size}") val maxSize: Int,
    @Value("\${spring.r2dbc.pool.max-idle-time}") val maxIdleTime: String,
    @Value("\${spring.r2dbc.pool.max-acquire-time}") val maxAcquireTime: String,
    @Value("\${spring.r2dbc.pool.acquire-retry}") val acquireRetry: Int,
    @Value("\${spring.r2dbc.pool.max-create-connection-time}") val maxCreateConnectionTime: String,
    @Value("\${spring.r2dbc.pool.validation-query}") val validationQuery: String
) {

    @Bean
    fun connectionPool(): ConnectionPool {
        val connectionFactory = PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .database(dbName)
                .username(userName)
                .password(password)
                .build()
        )
        val connectionPoolConfiguration = ConnectionPoolConfiguration.builder()
            .connectionFactory(connectionFactory)
            .initialSize(initialSize)
            .maxSize(maxSize)
            .maxIdleTime(Duration.parse(maxIdleTime))
            .maxAcquireTime(Duration.parse(maxAcquireTime))
            .acquireRetry(acquireRetry)
            .maxCreateConnectionTime(Duration.parse(maxCreateConnectionTime))
            .validationQuery(validationQuery)
            .build()
        return ConnectionPool(connectionPoolConfiguration)
    }
}
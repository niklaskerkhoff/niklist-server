package de.nikstack.niklist_server.lib.spring.security.core

data class UserMailValidationData(
    val email: String,
    val code: String,
)

data class GenericUsername<T>(val username: T)

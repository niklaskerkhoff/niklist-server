package de.nikstack.niklist_server.lib.spring.security.mail_login.core

data class UserMailValidationData(
    val email: String,
    val code: String,
)

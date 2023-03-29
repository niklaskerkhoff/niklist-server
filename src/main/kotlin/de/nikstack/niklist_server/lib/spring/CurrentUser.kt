package de.nikstack.niklist_server.lib.spring

import de.nikstack.niklist_server.lib.spring.entities.AppEntity
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ResponseStatusException

fun getCurrentUser(): String =
    SecurityContextHolder.getContext().authentication.name

fun AppEntity.isCreatedByCurrentUser() =
    this.createdBy == getCurrentUser()

fun AppEntity.isNotCreatedByCurrentUser() =
    !isCreatedByCurrentUser()

fun AppEntity.ensureCreatedByCurrentUser() {
    if (isNotCreatedByCurrentUser()) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}

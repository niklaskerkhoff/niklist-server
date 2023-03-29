package de.nikstack.niklist_server.lib.spring.entities

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

fun <T> EntityRepo<T>.findByIdOrThrow(id: UUID) =
    this.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "$id not found")

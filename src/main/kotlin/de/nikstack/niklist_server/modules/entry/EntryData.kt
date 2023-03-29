package de.nikstack.niklist_server.modules.entry

import java.time.LocalDateTime
import java.util.*

data class EntrySimpleData(
    val id: UUID,
    val listId: UUID,
    val title: String,
    val description: String = "",
    val deadline: LocalDateTime? = null
)

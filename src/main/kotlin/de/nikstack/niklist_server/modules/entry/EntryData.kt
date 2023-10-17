package de.nikstack.niklist_server.modules.entry

import java.time.LocalDateTime

data class EntrySimpleData(
    val title: String,
    val description: String = "",
    val deadline: LocalDateTime? = null
)

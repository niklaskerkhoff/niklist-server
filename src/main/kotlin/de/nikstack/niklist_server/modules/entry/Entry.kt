package de.nikstack.niklist_server.modules.entry

import de.nikstack.niklist_server.lib.spring.entities.AppEntity
import de.nikstack.niklist_server.modules.entry_list.EntryList
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Entry(
    var title: String,
    var description: String,
    var done: Boolean,
    var deadline: LocalDateTime? = null,
) : AppEntity()

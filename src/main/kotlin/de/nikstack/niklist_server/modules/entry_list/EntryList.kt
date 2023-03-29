package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.lib.spring.entities.AppEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

@Entity
class EntryList(
    var name: String,
    var color: String,

    @ElementCollection
    var accesses: List<EntryListAccess>
) : AppEntity()

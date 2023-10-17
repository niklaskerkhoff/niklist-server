package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.lib.spring.entities.AppEntity
import de.nikstack.niklist_server.modules.entry.Entry
import jakarta.persistence.CascadeType
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class EntryList(
    var name: String,
    var color: String,

    @OneToMany(cascade = [CascadeType.REMOVE])
    var entries: MutableList<Entry>,

    @ElementCollection
    var accesses: List<EntryListAccess>
) : AppEntity()

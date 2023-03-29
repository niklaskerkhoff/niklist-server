package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.modules.simple_user.AppUser
import jakarta.persistence.Embeddable
import jakarta.persistence.ManyToOne

@Embeddable
data class EntryListAccess(
    @ManyToOne
    val user: AppUser,
    val type: EntryListAccessType,
)

enum class EntryListAccessType {
    READ,
    WRITE
}

data class EntryListEmailAccess(
    val email: String,
    val type: EntryListAccessType
)

data class EntryListInfo(
    val name: String,
    val color: String
)

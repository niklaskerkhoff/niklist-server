package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.lib.spring.entities.EntityRepo
import org.springframework.stereotype.Repository

@Repository
interface EntryListRepo : EntityRepo<EntryList> {
    fun findAllByCreatedBy(email: String): List<EntryList>

    fun findAllByAccessesUserEmail(email: String): List<EntryList>
}

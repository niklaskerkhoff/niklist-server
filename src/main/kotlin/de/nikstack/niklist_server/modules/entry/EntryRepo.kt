package de.nikstack.niklist_server.modules.entry

import de.nikstack.niklist_server.lib.spring.entities.EntityRepo
import de.nikstack.niklist_server.modules.entry_list.EntryList
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EntryRepo : EntityRepo<Entry>

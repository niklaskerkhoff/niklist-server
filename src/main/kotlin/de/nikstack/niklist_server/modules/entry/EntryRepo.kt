package de.nikstack.niklist_server.modules.entry

import de.nikstack.niklist_server.lib.spring.entities.EntityRepo
import org.springframework.stereotype.Repository

@Repository
interface EntryRepo : EntityRepo<Entry>

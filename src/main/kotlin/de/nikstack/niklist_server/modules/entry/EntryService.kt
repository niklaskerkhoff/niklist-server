package de.nikstack.niklist_server.modules.entry

import de.nikstack.niklist_server.lib.spring.ensureCreatedByCurrentUser
import de.nikstack.niklist_server.lib.spring.entities.findByIdOrThrow
import de.nikstack.niklist_server.modules.entry_list.EntryListService
import org.springframework.stereotype.Service
import java.util.*

@Service
class EntryService(
    private val entryRepo: EntryRepo,
    private val entryListService: EntryListService
) {
    fun add(listId: UUID, title: String): Entry {
        val list = entryListService.getByIdOrThrow(listId)
        val entry = Entry(
            title,
            "",
            false,
            list
        )
        return entryRepo.save(entry)
    }

    fun update(id: UUID, data: EntrySimpleData): Entry {
        val entry = entryRepo.findByIdOrThrow(id)
        entry.ensureCreatedByCurrentUser()

        entry.title = data.title
        entry.description = data.description
        entry.deadline = data.deadline

        return entryRepo.save(entry)
    }

    fun updateDone(id: UUID, done: Boolean): Entry {
        val entry = entryRepo.findByIdOrThrow(id)
        entry.ensureCreatedByCurrentUser()

        entry.done = done

        return entryRepo.save(entry)
    }

    fun delete(id: UUID) {
        val entry = entryRepo.findByIdOrThrow(id)
        entry.ensureCreatedByCurrentUser()
    }
}

package de.nikstack.niklist_server.modules.entry

import de.nikstack.niklist_server.lib.spring.ensureCreatedByCurrentUser
import de.nikstack.niklist_server.lib.spring.entities.findByIdOrThrow
import de.nikstack.niklist_server.lib.spring.getCurrentUserEmail
import de.nikstack.niklist_server.modules.entry_list.EntryList
import de.nikstack.niklist_server.modules.entry_list.EntryListService
import org.springframework.stereotype.Service
import java.util.*

@Service
class EntryService(
    private val entryRepo: EntryRepo,
) {
    fun add(addEntry: EntrySimpleData): Entry {
        val entry = Entry(
            addEntry.title,
            addEntry.description,
            false,
            addEntry.deadline
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

    private fun EntryList.ensureAccessByCurrentUser(): Boolean {
        val currentUserEmail = getCurrentUserEmail()
        return createdBy == currentUserEmail
                || accesses.any { it.user.email == currentUserEmail }
    }
}

package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.lib.spring.entities.findByIdOrThrow
import de.nikstack.niklist_server.lib.spring.ensureCreatedByCurrentUser
import de.nikstack.niklist_server.lib.spring.getCurrentUserEmail
import de.nikstack.niklist_server.lib.spring.mails.SimpleMailService
import de.nikstack.niklist_server.modules.entry.EntryService
import de.nikstack.niklist_server.modules.entry.EntrySimpleData
import de.nikstack.niklist_server.modules.simple_user.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class EntryListService(
    private val entryListRepo: EntryListRepo,
    private val userService: UserService,
    private val simpleMailService: SimpleMailService,
    private val entryService: EntryService
) {

    fun getAll(): List<EntryList> {
        val email = getCurrentUserEmail()
        val ownLists = entryListRepo.findAllByCreatedBy(email)
        val accessLists = entryListRepo.findAllByAccessesUserEmail(email)
        return ownLists + accessLists
    }

    fun getByIdOrThrow(id: UUID): EntryList {
        return entryListRepo.findByIdOrThrow(id)
    }

    fun add(name: String, color: String): EntryList {
        val saveName = getTrimmedNotEmptyOrThrow(name)

        val list = EntryList(
            saveName,
            color,
            mutableListOf(),
            emptyList()
        )
        return entryListRepo.save(list)
    }

    fun delete(id: UUID) {
        val list = entryListRepo.findByIdOrThrow(id)
        list.ensureCreatedByCurrentUser()
        entryListRepo.delete(list)
    }

    fun updateInfo(id: UUID, name: String, color: String): EntryList {
        val saveName = getTrimmedNotEmptyOrThrow(name)

        val list = entryListRepo.findByIdOrThrow(id)
        list.ensureCreatedByCurrentUser()

        list.name = saveName
        list.color = color

        return entryListRepo.save(list)
    }

    fun updateAccesses(
        id: UUID,
        emailAccesses: List<EntryListEmailAccess>
    ): EntryList {
        val list = entryListRepo.findByIdOrThrow(id)
        list.ensureCreatedByCurrentUser()

        val emailUserAccesses = getEmailUserAccesses(emailAccesses)
        handleEmailsNotFound(emailUserAccesses)
        val userAccesses = emailUserAccesses.mapNotNull { it.value }
        val newAccesses = userAccesses.filterNot { list.accesses.contains(it) }
        list.accesses = userAccesses
        sendMailToNewAccesses(newAccesses)

        return entryListRepo.save(list)
    }

    fun addEntry(listId: UUID, addEntry: EntrySimpleData): EntryList {
        val list = entryListRepo.findByIdOrThrow(listId)
        list.ensureCreatedByCurrentUser()

        val entry = entryService.add(addEntry)
        list.entries.add(entry)

        return entryListRepo.save(list)
    }

    private fun getTrimmedNotEmptyOrThrow(name: String): String {
        val saveName = name.trim()
        if (saveName.isEmpty())
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        return saveName
    }

    private fun getEmailUserAccesses(emailAccesses: List<EntryListEmailAccess>)
            : Map<String, EntryListAccess?> {

        val emailUserAccesses = emailAccesses.map {
            when (val user = userService.getUserByEmail(it.email)) {
                null -> it.email to null
                else -> it.email to EntryListAccess(user, it.type)
            }
        }
        return emailUserAccesses.toMap()
    }

    private fun handleEmailsNotFound(
        emailUserAccesses: Map<String, EntryListAccess?>
    ) {
        val emailsNotFound = emailUserAccesses
            .filter { it.value == null }
            .map { it.key }

        if (emailsNotFound.isNotEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                emailsNotFound.joinToString(",")
            )
        }
    }

    private fun sendMailToNewAccesses(newAccesses: List<EntryListAccess>) {
        newAccesses.forEach {
            simpleMailService.sendMail(
                it.user.email,
                "Geteilte Liste",
                "Du hast Zugriff auf eine Liste erhalten. " +
                        "Gehe in die App, um sie dir anzusehen"
            )
        }
    }
}

package de.nikstack.niklist_server.modules.entry

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("entries")
class EntryController(
    private val entryService: EntryService
) {

    @PutMapping("{id}")
    fun update(@PathVariable id: UUID, @RequestBody entry: EntrySimpleData) =
        entryService.update(id, entry)

    @PatchMapping("{id}")
    fun updateDone(@PathVariable id: UUID, @RequestBody done: Boolean) =
        entryService.updateDone(id, done)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) = entryService.delete(id)
}

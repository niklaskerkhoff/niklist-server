package de.nikstack.niklist_server.modules.entry_list

import de.nikstack.niklist_server.modules.entry.EntrySimpleData
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("lists")
class EntryListController(
    private val entryListService: EntryListService
) {

    @GetMapping
    fun getAll() = entryListService.getAll()

    @PostMapping
    fun add(@RequestBody info: EntryListInfo) =
        entryListService.add(info.name, info.color)

    @PatchMapping("{id}/info")
    fun updateInfo(@PathVariable id: UUID, @RequestBody info: EntryListInfo) =
        entryListService.updateInfo(id, info.name, info.color)

    @PatchMapping("{id}/accesses")
    fun updateAccesses(
        @PathVariable id: UUID,
        @RequestBody emailAccesses: List<EntryListEmailAccess>
    ) = entryListService.updateAccesses(id, emailAccesses)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) = entryListService.delete(id)

    @PostMapping("{id}/entry")
    fun addEntry(
        @PathVariable id: UUID,
        @RequestBody addEntry: EntrySimpleData
    ) = entryListService.addEntry(id, addEntry)
}

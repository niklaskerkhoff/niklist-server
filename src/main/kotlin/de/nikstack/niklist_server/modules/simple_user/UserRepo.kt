package de.nikstack.niklist_server.modules.simple_user

import de.nikstack.niklist_server.lib.spring.entities.EntityRepo
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : EntityRepo<AppUser> {
    fun findByEmail(email: String): AppUser?
}

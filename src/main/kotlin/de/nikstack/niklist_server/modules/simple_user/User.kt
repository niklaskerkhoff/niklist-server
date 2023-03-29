package de.nikstack.niklist_server.modules.simple_user

import de.nikstack.niklist_server.lib.spring.entities.AppEntity
import jakarta.persistence.Entity

@Entity
class User(
    var email: String,
    var name: String,
    var role: UserRole,
    var password: String
) : AppEntity()

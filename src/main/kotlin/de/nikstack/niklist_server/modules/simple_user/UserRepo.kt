package de.nikstack.niklist_server.modules.simple_user

import de.nikstack.niklist_server.lib.spring.entities.AppEntityRepo
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : AppEntityRepo<User>

package de.nikstack.niklist_server.modules.simple_user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo
) {

    fun getUserByEmail(email: String): AppUser? {
        TODO("Not yet implemented")
    }

    fun addUser(email: String): AppUser {
        TODO("Not yet implemented")
    }
}

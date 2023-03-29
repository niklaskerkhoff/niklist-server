package de.nikstack.niklist_server.modules.simple_user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo
) {

    fun getUserByEMail(email: String): User? {
        TODO("Not yet implemented")
    }

    fun addUser(email: String): User {
        TODO("Not yet implemented")
    }
}
